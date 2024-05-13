package com.stori.interviewtest.ui.screens.signin

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.stori.interviewtest.presentation.R.string
import com.stori.interviewtest.ui.Utils.showMessage
import com.stori.interviewtest.ui.components.ProgressBar
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewEvent.SignInEvent
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewState.Failure
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewState.Init
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewState.Loading
import com.stori.interviewtest.ui.screens.signin.SignInScreenViewState.Success
import com.stori.interviewtest.ui.screens.signin.components.SignInContent
import com.stori.interviewtest.ui.screens.signin.components.SignInTopBar

@Composable
@ExperimentalComposeUiApi
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToSignUpScreen: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SignInTopBar(stringResource(string.app_name),false)
        },
        content = { padding ->
            SignInContent(
                padding = padding,
                signIn = { email, password ->
                    viewModel.postEvent(SignInEvent(email, password))
                },
                navigateToSignUpScreen = {
                    viewModel.cleanState()
                    navigateToSignUpScreen()
                }
            )
        }
    )

    val viewState by viewModel.viewState.collectAsState()

    when (val state= viewState) {
        is Init -> {}
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> {
            LaunchedEffect(state.e) {
                print(state.e)
                showMessage(context, state.e)
            }
        }
    }
}
