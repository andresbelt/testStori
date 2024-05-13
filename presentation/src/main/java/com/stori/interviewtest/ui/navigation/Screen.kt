package com.stori.interviewtest.ui.navigation

//Screens
const val SIGN_IN_SCREEN = "Sign in"
const val SIGN_UP_SCREEN = "Sign up"
const val VERIFY_EMAIL_SCREEN = "Verify email"
const val PROFILE_SCREEN = "Profile"


sealed class Screen(val route: String) {
    object SignInScreen: Screen(SIGN_IN_SCREEN)
    object SignUpScreen: Screen(SIGN_UP_SCREEN)
    object SuccessScreen: Screen(VERIFY_EMAIL_SCREEN)
    object HomeScreen: Screen(PROFILE_SCREEN)
}
