package com.ob.marvelapp.data.local.db

import com.ob.data.datasources.LocalDataSource
import com.ob.domain.Either
import com.ob.domain.Failure
import com.ob.domain.Hero
import com.ob.marvelapp.data.local.DBMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

class RoomDB(private val dbMapper: DBMapper, database: DataBase) : LocalDataSource {

    private val dao: HeroDao = database.heroDao()

    object EmptyList : Failure.CustomFailure()

    override suspend fun getHeroes(): Flow<Either<Failure, List<Hero>>> {
        return withContext(Dispatchers.IO) {
            dao.getHeroes()
                .distinctUntilChanged()
                .mapLatest { list ->
                    if (list.isEmpty()) {
                        Either.Left(EmptyList)
                    } else {
                        Either.Right(dbMapper.convertDBHeroesToDomain(list))
                    }
                }
        }
    }

    override suspend fun getHero(id: Int): Flow<Either<Failure, Hero>> {
        return withContext(Dispatchers.IO) {
            dao.getHero(id)
                .distinctUntilChanged()
                .mapLatest { item ->
                    if (item == null) {
                        Either.Left(Failure.NullResult)
                    } else {
                        Either.Right(dbMapper.convertDBHeroToDomain(item))
                    }
                }
        }
    }

    override suspend fun insertHeroes(heroes: List<Hero>) {
        withContext(Dispatchers.IO) {
            dao.insertHeroes(dbMapper.convertHeroesToDBHeroes(heroes))
        }
    }
}