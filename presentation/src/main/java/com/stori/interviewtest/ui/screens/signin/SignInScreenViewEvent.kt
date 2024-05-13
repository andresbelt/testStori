package com.stori.interviewtest.ui.screens.signin

sealed interface SignInScreenViewEvent {
    data class SignInEvent(val email:String, val password: String): SignInScreenViewEvent
}
