package com.ob.data


import com.ob.data.datasources.LocalDataSource
import com.ob.data.datasources.RemoteDataSource
import com.ob.domain.Constants.Server.API_KEY
import com.ob.domain.Constants.Server.PRIVATE_KEY
import com.ob.domain.Either
import com.ob.domain.Failure
import com.ob.domain.Hero
import com.ob.domain.flatMapToRight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest


interface MarvelRepository {

    suspend fun getHeroes(fromNetwork: Boolean = true): Flow<Either<Failure, List<Hero>>>

    suspend fun getHero(id: Int): Flow<Either<Failure, Hero>>
}

class MarvelRepositoryImplementation(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MarvelRepository {

    override suspend fun getHeroes(fromNetwork: Boolean): Flow<Either<Failure, List<Hero>>> {
        val apiKey = API_KEY
        val timeStampString = (System.currentTimeMillis() / 1000L).toString()
        val hash = Utils.md5(timeStampString + PRIVATE_KEY + API_KEY)

        return when (fromNetwork) {
            true -> networkDataSourceSearchHeroes(timeStampString, apiKey, hash)
            false -> localDataSource.getHeroes()
                .distinctUntilChanged()
                .flatMapLatest {
                    networkDataSourceSearchHeroes(timeStampString, apiKey, hash)
                }
        }
    }

    override suspend fun getHero(id: Int): Flow<Either<Failure, Hero>> {
        return localDataSource.getHero(id)
    }


    private suspend fun networkDataSourceSearchHeroes(
        timeStamp: String,
        apiKey: String,
        hash: String
    ): Flow<Either<Failure, List<Hero>>> {

        remoteDataSource.getHeroes(timeStamp, apiKey, hash).flatMapToRight { heroes ->
            localDataSource.insertHeroes(heroes)
            Either.Right(heroes)
        }

        return localDataSource.getHeroes()
    }
}