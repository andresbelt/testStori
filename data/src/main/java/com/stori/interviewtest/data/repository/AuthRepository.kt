package com.stori.interviewtest.data.repository

import com.google.firebase.auth.FirebaseUser
import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.Response.Failure
import com.stori.interviewtest.data.Response.Success
import com.stori.interviewtest.data.error.GenericUserErrorContainer
import com.stori.interviewtest.data.error.UserErrorContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String):Either<GenericUserErrorContainer, Boolean>

    fun signOut()

    fun getAuthState(viewModelScope: CoroutineScope): StateFlow<Boolean>

    fun saveInfoUser(user: UserStori): Flow<Either<UserErrorContainer, Boolean>>
}
