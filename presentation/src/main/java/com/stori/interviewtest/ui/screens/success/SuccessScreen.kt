package com.stori.interviewtest.ui.screens.success

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.stori.interviewtest.presentation.R.string
import com.stori.interviewtest.ui.screens.signin.components.SignInTopBar

@Composable
fun SuccessScreen(
    navigateToProfileScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            SignInTopBar(stringResource(string.app_name),false)
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.clickable {
                        navigateToProfileScreen()
                    },
                    text = stringResource(id = string.success_account),
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    )
}
