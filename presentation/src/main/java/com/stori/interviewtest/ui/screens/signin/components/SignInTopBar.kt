package com.stori.interviewtest.ui.screens.signin.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.stori.interviewtest.presentation.R
import com.stori.interviewtest.ui.components.BackIcon

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SignInTopBar(
    title: String,
    needDrawer: Boolean,
    navigateBack: (() -> Unit)? = null
) {
    TopAppBar (
        navigationIcon = {
            if (needDrawer) {
                BackIcon(
                    navigateBack = navigateBack!!
                )
            }
        },
        title = {
            Text(
                text = title
            )
        }
    )
}
