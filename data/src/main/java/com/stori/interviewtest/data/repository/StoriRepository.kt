package com.stori.interviewtest.data.repository

import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.Response.Success
import com.stori.interviewtest.data.error.UserErrorContainer
import com.stori.interviewtest.data.models.Movement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StoriRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Either<UserErrorContainer, Boolean>
    fun getAuthState(viewModelScope: CoroutineScope): StateFlow<Boolean>
    fun saveInfoUser(user: UserStori): Flow<Either<UserErrorContainer, Boolean>>
    fun getlistMovements(): Flow<Either<UserErrorContainer, List<Movement?>?>>
    fun signOut()
    fun getUser()
    fun loadProfile(): Flow<Either<UserErrorContainer, UserStori?>>
}
