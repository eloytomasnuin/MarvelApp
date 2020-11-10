package com.ob.marvelapp.data.remote.manager

import com.ob.domain.Either
import com.ob.domain.Failure
import com.ob.domain.flatMapToRight
import retrofit2.Response

interface NetworkManager {

    class ServerResponseException(val errorCode: Int, val message: String?) :
        Failure.CustomFailure()

    class UnexpectedServerError(val errorCode: Int, val message: String?) : Failure.CustomFailure()
    class EmptyBody : Failure.CustomFailure()


    suspend fun <T, R> safeRequest(
        callRequest: Response<T>,
        functionCall: (Either.Right<T>) -> Either<Failure, R>
    ): Either<Failure, R>


    class NetworkImplementation : NetworkManager {

        override suspend fun <T, R> safeRequest(
            callRequest: Response<T>,
            functionCall: (Either.Right<T>) -> Either<Failure, R>
        ): Either<Failure, R> {

            return ((if (callRequest.isSuccessful) {
                val body = callRequest.body()

                if (body != null)
                    Either.Right(body)
                else
                    Either.Left(EmptyBody())

            } else {
                when (callRequest.code()) {
                    in 300..600 -> Either.Left(
                        ServerResponseException(
                            callRequest.code(),
                            callRequest.errorBody()?.string()
                        )
                    )
                    else -> Either.Left(
                        UnexpectedServerError(
                            callRequest.code(),
                            callRequest.errorBody()?.string()
                        )
                    )
                }
            }).flatMapToRight { rightResult -> functionCall.invoke(Either.Right(rightResult)) })
        }
    }
}