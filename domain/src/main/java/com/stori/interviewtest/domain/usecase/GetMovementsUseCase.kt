package com.stori.interviewtest.domain.usecase

import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.data.error.UserErrorContainer
import com.stori.interviewtest.data.models.Movement
import com.stori.interviewtest.data.repository.StoriRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMovementsUseCase @Inject constructor(
    private val repository: StoriRepository
) {
    fun getlistMovements(): Flow<Either<UserErrorContainer, List<Movement?>?>>  {
        return repository.getlistMovements()
    }
}
