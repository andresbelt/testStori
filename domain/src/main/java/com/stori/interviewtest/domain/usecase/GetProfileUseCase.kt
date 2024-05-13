package com.stori.interviewtest.domain.usecase

import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.error.UserErrorContainer
import com.stori.interviewtest.data.repository.StoriRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetProfileUseCase @Inject constructor(
    private val repository: StoriRepository
) {
    fun getInfoProfile(): Flow<Either<UserErrorContainer, UserStori?>>  {
        return repository.loadProfile()
    }
}
