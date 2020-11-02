package com.ob.usecases

import com.ob.domain.DomainEntities.Failure
import com.ob.domain.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

abstract class UseCase<Params, Return> {

    suspend operator fun invoke(params: Params) : Flow<Either<Failure, Return>> {
        return execute(params).flowOn(Dispatchers.IO)
    }

    protected abstract suspend fun execute(params: Params): Flow<Either<Failure, Return>>
}