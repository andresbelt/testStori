package com.stori.interviewtest.ui.screens.signup

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.stori.interviewtest.presentation.R.string
import com.stori.interviewtest.ui.Utils.showMessage
import com.stori.interviewtest.ui.screens.signin.components.SignInTopBar
import com.stori.interviewtest.ui.screens.signup.components.SignUpContent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@ExperimentalComposeUiApi
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToSuccessScreen: () -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SignInTopBar(stringResource(string.sign_up_title),true) { navigateBack() }
        },
        content = {
            SignUpContent(
                viewModel = viewModel,
                navigateBack = navigateBack,
                navigateToSuccessScreen = {navigateToSuccessScreen()},
                showMessage = { showMessage(context, it) }
            )
        }
    )
}
