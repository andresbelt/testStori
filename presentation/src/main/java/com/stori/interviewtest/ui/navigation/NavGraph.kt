package com.stori.interviewtest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stori.interviewtest.ui.navigation.Screen.HomeScreen
import com.stori.interviewtest.ui.navigation.Screen.SignInScreen
import com.stori.interviewtest.ui.navigation.Screen.SignUpScreen
import com.stori.interviewtest.ui.navigation.Screen.SuccessScreen
import com.stori.interviewtest.ui.screens.home.HomeProfile
import com.stori.interviewtest.ui.screens.signin.SignInScreen
import com.stori.interviewtest.ui.screens.signup.SignUpScreen
import com.stori.interviewtest.ui.screens.success.SuccessScreen

@Composable
@ExperimentalComposeUiApi
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SignInScreen.route
    ) {
        composable(
            route = SignInScreen.route
        ) {
            SignInScreen(
                navigateToSignUpScreen = {
                    navController.navigate(SignUpScreen.route)
                }
            )
        }
        composable(
            route = SignUpScreen.route
        ) {
            SignUpScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToSuccessScreen = {
                    navController.navigate(SuccessScreen.route)
                }
            )
        }
        composable(
            route = SuccessScreen.route
        ) {
            SuccessScreen(
                navigateToProfileScreen = {
                    navController.navigate(HomeScreen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = HomeScreen.route
        ) {
            HomeProfile()
        }
    }
}
