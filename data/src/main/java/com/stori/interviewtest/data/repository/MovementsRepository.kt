package com.stori.interviewtest.data.repository

import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.data.error.UserErrorContainer
import com.stori.interviewtest.data.models.Movement
import kotlinx.coroutines.flow.Flow

interface MovementsRepository {
    fun loadMovements(): Flow<Either<UserErrorContainer,List<Movement?>?>>
}
