package com.stori.interviewtest.ui.screens.signup

sealed interface SignUpScreenViewState {
    object Init : SignUpScreenViewState
    object Loading : SignUpScreenViewState
    data class Success(val data: Boolean = false) : SignUpScreenViewState
    data class Failure(val e: String): SignUpScreenViewState
}
