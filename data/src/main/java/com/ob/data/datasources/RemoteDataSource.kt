package com.ob.data.datasources



import com.ob.domain.Either
import com.ob.domain.Failure
import com.ob.domain.Hero

interface RemoteDataSource {
    suspend fun getHeroes(): Either<Failure, List<Hero>>
}