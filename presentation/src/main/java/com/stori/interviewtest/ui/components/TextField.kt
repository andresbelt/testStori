package com.stori.interviewtest.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.job

@Composable
fun TextField(
    text: TextFieldValue,
    labelText: String,
    focus: Boolean,
    onTextValueChange: (newValue: TextFieldValue) -> Unit
) {
    val focusRequester = FocusRequester()

    OutlinedTextField(
        value = text,
        onValueChange = { newValue ->
            onTextValueChange(newValue)
        },
        label = {
            Text(
                text = labelText
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
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

