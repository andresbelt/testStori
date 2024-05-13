package com.stori.interviewtest.ui.screens.signup

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase.ParamsSignUp
import com.stori.interviewtest.ui.BaseViewModel
import com.stori.interviewtest.ui.FilePicker
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.OnTakePhoto
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SetEmail
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SetName
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SetPassword
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SetUsername
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewEvent.SignUpEvent
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewState.Failure
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewState.Init
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewState.Loading
import com.stori.interviewtest.ui.screens.signup.SignUpScreenViewState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getAllBets: AuthProfileUseCase,
    private val filePicker: FilePicker
) : BaseViewModel<SignUpScreenViewEvent, SignUpScreenViewState>() {

    private val _photoFilePath = MutableStateFlow<File?>(null)
    val photoFilePath: StateFlow<File?> = _photoFilePath

    // controls if the image file must be deleted after the screen is closed
    private var mustDeleteCoverImage: Boolean = true

    // temporary image file used by the camera app
    private var tempImageFile: File? = null
    var areAllFieldsValid: Boolean = false

    private var currentUser = UserStori()

    private fun validateFields() {
        areAllFieldsValid = isUserValid(currentUser)
    }

    private fun setEmail(email: String) {
        currentUser = currentUser.copy(email = email)
        validateFields()
    }

    private fun setPassword(password: String) {
        currentUser = currentUser.copy(password = password)
        validateFields()
    }

    private fun setName(name: String) {
        currentUser = currentUser.copy(name = name)
        validateFields()
    }

    private fun setUsername(username: String) {
        currentUser = currentUser.copy(username = username)
        validateFields()
    }

    private fun isEmailValid(book: UserStori) = book.email.isNotBlank()
    private fun isPasswordValid(book: UserStori) = book.password.isNotBlank()
    private fun isUsernameValid(book: UserStori) = book.username.isNotBlank()
    private fun isNameValid(book: UserStori) = book.name.isNotBlank()
    private fun isPhotoValid(book: UserStori) = book.photoUrl.isNotBlank()

    private fun isUserValid(book: UserStori): Boolean {
        return (
                isEmailValid(book) &&
                        isPasswordValid(book) &&
                        isUsernameValid(book) &&
                        isNameValid(book) &&
                        isPhotoValid(book)
                )
    }

    fun createTempImageFile(): Uri {
        val file = filePicker.createTempFile()
        mustDeleteCoverImage = true
        // delete the previous tempImageFile before assign a new one
        deleteTempPhoto()
        tempImageFile = file
        return filePicker.uriFromFile(file)
    }

    private fun assignCoverImage() {
        tempImageFile?.let {
            setPhoto("file://${it.absolutePath}")
        }
        _photoFilePath.value = tempImageFile
    }

    private fun setPhoto(tempImageFile: String) {
        currentUser = currentUser.copy(photoUrl = tempImageFile)
        validateFields()
    }

    private fun deleteTempPhoto() {
        if (mustDeleteCoverImage) {
            tempImageFile?.let {
                if (it.exists()) it.delete()
            }
        }
    }

    private fun handleSignUp() {
        viewModelScope.launch {
            setState(Loading)
            getAllBets.signUp(ParamsSignUp(currentUser)).collect { it ->
                it.fold(
                    functionLeft = { error ->
                        setState(Failure(error.userError.message))
                    }, functionRight = {data ->
                        setState(Success(data))
                    }
                )
            }
        }
    }

    override fun manageEvent(event: SignUpScreenViewEvent) {
        when (event) {
            is SignUpEvent -> handleSignUp()
            is OnTakePhoto -> assignCoverImage()
            is SetEmail -> setEmail(event.email)
            is SetPassword -> setPassword(event.password)
            is SetName -> setName(event.name)
            is SetUsername -> setUsername(event.username)
        }
    }


    override fun initialState(): SignUpScreenViewState {
        return Init
    }
}
