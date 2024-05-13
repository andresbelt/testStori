package com.stori.interviewtest.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.stori.interviewtest.presentation.R.string
import kotlinx.coroutines.job

@Composable
fun EmailField(
    email: TextFieldValue,
    focus: Boolean,
    onEmailValueChange: (newValue: TextFieldValue) -> Unit
) {
    val focusRequester = FocusRequester()

    OutlinedTextField(
        value = email,
        onValueChange = { newValue ->
            onEmailValueChange(newValue)
        },
        label = {
            Text(
                text = stringResource(string.email)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        modifier = Modifier.focusRequester(focusRequester)
    )
    if(focus){
        LaunchedEffect(Unit) {
            coroutineContext.job.invokeOnCompletion {
                focusRequester.requestFocus()
            }
        }
    }
}

