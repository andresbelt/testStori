package com.stori.interviewtest.ui.screens.signin

sealed interface SignInScreenViewState {
    object Loading : SignInScreenViewState
    object Init : SignInScreenViewState
    data class Success(val data: Boolean = false) : SignInScreenViewState
    data class Failure(val e: String): SignInScreenViewState
}
