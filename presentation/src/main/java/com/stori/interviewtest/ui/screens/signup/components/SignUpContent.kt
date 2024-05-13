package com.stori.interviewtest.ui.screens.signup.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.stori.interviewtest.presentation.R
import com.stori.interviewtest.presentation.R.string
import com.stori.interviewtest.ui.Utils
import com.stori.interviewtest.ui.components.EmailField
import com.stori.interviewtest.ui.components.LoadingState
import com.stori.interviewtest.ui.components.PasswordField
import com.stori.interviewtest.ui.components.ProgressBar
import com.stori.interviewtest.ui.components.SmallSpacer
import com.stori.interviewtest.ui.components.TextField
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.OnTakePhoto
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SetEmail
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SetName
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SetPassword
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SetUsername
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SignUpEvent
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewState.Failure
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewState.Loading
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewState.Success
import com.stori.interviewtest.ui.screens.signup.SignUpViewModel

@Composable
@ExperimentalComposeUiApi
fun SignUpContent(
    navigateBack: () -> Unit,
    viewModel: SignUpViewModel,
    showMessage: (message: String) -> Unit,
    navigateToSuccessScreen: () -> Unit
) {
    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()
    val message = stringResource(string.error_take_photo)

    val launcher = rememberLauncherForActivityResult(
        TakePicture()
    ) { result ->
        if (result) {
            viewModel.postEvent(OnTakePhoto)
        } else {
            showMessage(message)
        }
    }
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = ""
                )
            )
        }
    )
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = ""
                )
            )
        }
    )
    var username by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = ""
                )
            )
        }
    )
    var name by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = ""
                )
            )
        }
    )
    val keyboard = LocalSoftwareKeyboardController.current
    val photoFilePath by viewModel.photoFilePath.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            photoFilePath?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .size(200.dp),
                    contentScale = ContentScale.FillBounds
                )
            } ?: run {
                val imageModifier = Modifier
                    .background(Color.Gray)
                    .clickable {
                        val uri = viewModel.createTempImageFile()
                        launcher.launch(uri)
                    }
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_camera),
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .then(imageModifier)
                        .padding(40.dp),
                )
            }
        }
        TextField(
            text = name,
            labelText = stringResource(string.name),
            focus = true,
            onTextValueChange = { newValue ->
                name = newValue
                viewModel.postEvent(SetName(name.text))
            }
        )
        SmallSpacer()
        TextField(
            text = username,
            labelText = stringResource(string.username),
            focus = false,
            onTextValueChange = { newValue ->
                username = newValue
                viewModel.postEvent(SetUsername(username.text))
            }
        )
        SmallSpacer()
        EmailField(
            email = email,
            focus = false,
            onEmailValueChange = { newValue ->
                email = newValue
                viewModel.postEvent(SetEmail(email.text))
            }
        )
        SmallSpacer()
        PasswordField(
            password = password,
            onPasswordValueChange = { newValue ->
                password = newValue
                viewModel.postEvent(SetPassword(password.text))
            }
        )
        SmallSpacer()
        Button(
            enabled = viewModel.areAllFieldsValid,
            onClick = {
                keyboard?.hide()
                viewModel.postEvent(SignUpEvent)
            }
        ) {
            Text(
                text = stringResource(string.sign_up_button),
                fontSize = 15.sp
            )
        }
        Text(
            modifier = Modifier.clickable {
                navigateBack()
            },
            text = stringResource(string.already_user),
            fontSize = 15.sp
        )
    }

    when (val state = viewState) {
        Loading -> LoadingState()
        is Success -> {
            val isUserSignedUp = state.data
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp) {
                    navigateToSuccessScreen()
                }
            }
        }
        is Failure -> {
            LaunchedEffect(state.e) {
                print(state.e)
                Utils.showMessage(context, state.e)
            }
        }
        else -> {}
    }
}
