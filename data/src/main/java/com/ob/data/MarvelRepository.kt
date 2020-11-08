package com.ob.data


import com.ob.data.datasources.LocalDataSource
import com.ob.data.datasources.RemoteDataSource
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
    private val localDataSource: LocalDataSource): MarvelRepository {

    override suspend fun getHeroes(fromNetwork: Boolean): Flow<Either<Failure, List<Hero>>> {
        return when (fromNetwork) {
            true -> networkDataSourceSearchHeroes()
            false -> localDataSource.getHeroes()
                .distinctUntilChanged()
                .flatMapLatest {
                    networkDataSourceSearchHeroes()
                }
        }
    }

    override suspend fun getHero(id: Int): Flow<Either<Failure, Hero>> {
        return localDataSource.getHero(id)
    }


    private suspend fun networkDataSourceSearchHeroes(): Flow<Either<Failure, List<Hero>>> {

        remoteDataSource.getHeroes().flatMapToRight { heroes ->
            localDataSource.insertHeroes(heroes)
            Either.Right(heroes)
        }

        return localDataSource.getHeroes()
    }
}