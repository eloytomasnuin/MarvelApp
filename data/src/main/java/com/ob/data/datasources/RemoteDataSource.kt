package com.ob.data.datasources


import com.ob.domain.DomainEntities.*
import com.ob.domain.Either

interface RemoteDataSource {
    suspend fun getHeroes(): Either<Failure, List<Hero>>
}