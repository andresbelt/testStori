package com.stori.interviewtest.ui.screens.home

import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.models.Movement

sealed interface HomeScreenViewState {
    object Loading : HomeScreenViewState
    data class ListDisplay(val movements: List<Movement> = emptyList()) : HomeScreenViewState
    data class ProfileUpdate(val movements: UserStori? = null) : HomeScreenViewState
}
