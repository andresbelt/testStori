package com.stori.interviewtest.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.models.Movement
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase
import com.stori.interviewtest.domain.usecase.GetMovementsUseCase
import com.stori.interviewtest.domain.usecase.GetProfileUseCase
import com.stori.interviewtest.ui.BaseViewModel
import com.stori.interviewtest.ui.screens.home.HomeScreenViewEvent.Init
import com.stori.interviewtest.ui.screens.home.HomeScreenViewEvent.SignOut
import com.stori.interviewtest.ui.screens.home.HomeScreenViewState.Loading
import com.stori.interviewtest.ui.screens.home.HomeScreenViewState.ProfileUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfile: GetProfileUseCase,
    private val getMovements: GetMovementsUseCase,
    private val authUseCase: AuthProfileUseCase
) : BaseViewModel<HomeScreenViewEvent, HomeScreenViewState>() {

    private val _listMovements = MutableStateFlow<List<Movement?>?>(emptyList())
    val listMovements: StateFlow<List<Movement?>?> = _listMovements
    private val _profile = MutableStateFlow<UserStori?>(null)
    val profile: StateFlow<UserStori?> = _profile

    private fun fetchInfo() {
        getInfoUser()
        getMovements()
    }

    private fun getInfoUser() {
        viewModelScope.launch {
            getProfile.getInfoProfile().collect {
                it.fold(
                    functionLeft = {
                        _profile.value = null
                    }, functionRight = { data ->
                        _profile.value = data
                    }
                )
            }
        }}

    private fun getMovements() {
        viewModelScope.launch {
            getMovements.getlistMovements().collect {
                it.fold(
                    functionLeft = {
                        setState(ProfileUpdate(null))
                    }, functionRight = { data ->
                        _listMovements.value = data
                    }
                )
            }
        }
    }

    override fun manageEvent(event: HomeScreenViewEvent) {
        when (event) {
            is Init -> fetchInfo()
            is SignOut -> authUseCase.signOut()
        }
    }

    override fun initialState(): HomeScreenViewState {
        return Loading
    }
}
