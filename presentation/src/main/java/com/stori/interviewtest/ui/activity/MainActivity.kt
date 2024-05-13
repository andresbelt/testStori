package com.stori.interviewtest.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stori.interviewtest.ui.navigation.NavGraph
import com.stori.interviewtest.ui.navigation.Screen.HomeScreen
import com.stori.interviewtest.ui.navigation.Screen.SignInScreen
import com.stori.interviewtest.ui.theme.ColorPrimary
import com.stori.interviewtest.ui.theme.SimpleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberNavController()
            SimpleTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ColorPrimary
                ) {
                    NavGraph(
                        navController = navController
                    )
                    AuthState()
                }
            }
        }
    }

    @Composable
    private fun AuthState() {
        val isUserSignedOut = mainViewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            NavigateToProfileScreen()
        }
    }

    @Composable
    private fun NavigateToSignInScreen() = navController.navigate(SignInScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToProfileScreen() = navController.navigate(HomeScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}
