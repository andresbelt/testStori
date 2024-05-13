package com.stori.interviewtest.data.repository

import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.error.UserErrorContainer
import com.stori.interviewtest.data.models.Movement
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class StoriDataRepository @Inject constructor(
    private val authFirebase: AuthRepositoryFirebase,
    private val movementsFirebase: MovementsRepositoryImpl,
    private val profileFirebase: ProfileRepositoryImpl,
    ) : StoriRepository {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Either<UserErrorContainer, Boolean> {
        return authFirebase.firebaseSignInWithEmailAndPassword(email, password)
    }

    override fun getAuthState(viewModelScope: CoroutineScope) =
        authFirebase.getAuthState(viewModelScope)

    override fun saveInfoUser(user: UserStori): Flow<Either<UserErrorContainer, Boolean>> =
        authFirebase.saveInfoUser(user)

    override fun getlistMovements(): Flow<Either<UserErrorContainer, List<Movement?>?>> {
        return movementsFirebase.loadMovements()
    }

    override fun signOut() {
        authFirebase.signOut()
    }

    override fun getUser() {
        val info = authFirebase.currentUser

    }

    override fun loadProfile(): Flow<Either<UserErrorContainer, UserStori?>> {
        return profileFirebase.loadProfile()
    }
}
