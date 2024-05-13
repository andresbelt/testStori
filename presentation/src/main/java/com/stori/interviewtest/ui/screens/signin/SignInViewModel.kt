package com.stori.interviewtest.ui.screens.signin

import androidx.lifecycle.viewModelScope
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase.ParamsSignIn
import com.stori.interviewtest.ui.BaseViewModel
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewEvent.SignInEvent
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewState.Failure
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewState.Init
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewState.Loading
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authUseCase: AuthProfileUseCase
): BaseViewModel<SignInScreenViewEvent, SignInScreenViewState>() {

    private fun handleSignIn(email: String, password: String) = viewModelScope.launch {
        setState(Loading)
        authUseCase.signIn(ParamsSignIn(email, password)).fold(
            functionRight = {
                setState(Success(true))
            },
            functionLeft = {
                setState(Failure(it.userError.message))
            }
        )
    }

    override fun manageEvent(event: SignInScreenViewEvent) {
        when (event) {
            is SignInEvent -> handleSignIn(event.email, event.password)
        }
    }

    override fun initialState(): SignInScreenViewState {
        return Init
    }
}
