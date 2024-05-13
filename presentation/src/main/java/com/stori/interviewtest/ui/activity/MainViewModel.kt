package com.stori.interviewtest.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authUseCase: AuthProfileUseCase
): ViewModel() {

    init {
        getAuthState()
    }

    fun getAuthState() = authUseCase.getAuthState(viewModelScope)
}
