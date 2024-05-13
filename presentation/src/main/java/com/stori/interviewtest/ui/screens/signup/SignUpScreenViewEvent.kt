package com.stori.interviewtest.ui.screens.signup

sealed interface SignUpScreenViewEvent {
    object SignUpEvent: SignUpScreenViewEvent
    object OnTakePhoto: SignUpScreenViewEvent
    data class SetEmail(val email:String): SignUpScreenViewEvent
    data class SetPassword(val password:String): SignUpScreenViewEvent
    data class SetName(val name:String): SignUpScreenViewEvent
    data class SetUsername(val username:String): SignUpScreenViewEvent
}
