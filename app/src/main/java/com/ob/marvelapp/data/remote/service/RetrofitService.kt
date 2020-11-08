package com.ob.marvelapp.data.remote.service

import com.ob.data.datasources.RemoteDataSource
import com.ob.domain.Either
import com.ob.domain.Failure
import com.ob.domain.Hero
import com.ob.marvelapp.data.remote.JsonMapper
import com.ob.marvelapp.data.remote.RemoteMapper
import com.ob.marvelapp.data.remote.manager.ConnectionManager
import com.ob.marvelapp.data.remote.manager.NetworkManager

class RetrofitService(
    private val retrofit: ApiService,
    private val serverMapper: RemoteMapper,
    private val jsonMapper: JsonMapper,
    private val connectivityManager: ConnectionManager
) : RemoteDataSource,
    NetworkManager by NetworkManager.NetworkImplementation() {

    object ConnectionError : Failure.CustomFailure()

    override suspend fun getHeroes(): Either<Failure, List<Hero>> =
        when (connectivityManager.isConnected()) {

            true -> safeRequest(retrofit.getHeroes()) { listBeers ->
                jsonMapper.getArray(listBeers.b) { jsonArray ->
                    convertJsonToHeroes(jsonArray) { entity ->
                        serverMapper.convertEntityHeroToDomain(entity)
                    }
                }
            }

            false -> Either.Left(ConnectionError)
        }

}