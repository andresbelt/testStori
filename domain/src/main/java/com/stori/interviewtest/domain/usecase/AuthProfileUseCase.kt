package com.stori.interviewtest.domain.usecase

import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.error.UserErrorContainer
import com.stori.interviewtest.data.repository.StoriRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AuthProfileUseCase @Inject constructor(
    private val repository: StoriRepository
) {

    fun signUp(params: ParamsSignUp): Flow<Either<UserErrorContainer, Boolean>>  {
        return repository.saveInfoUser(params.user)
    }

    suspend fun signIn(params: ParamsSignIn): Either<UserErrorContainer, Boolean> {
        return repository.signInWithEmailAndPassword(params.email, params.password)
    }

    fun getAuthState(viewModelScope: CoroutineScope): StateFlow<Boolean> {
        return repository.getAuthState(viewModelScope)
    }

    fun signOut() {
        return repository.signOut()
    }

    data class ParamsSignIn(val email: String, val password: String)
    data class ParamsSignUp(val user: UserStori)
}
