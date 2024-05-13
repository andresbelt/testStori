package com.stori.interviewtest.ui.screens.home

sealed interface HomeScreenViewEvent {
    object Init: HomeScreenViewEvent
    object SignOut: HomeScreenViewEvent
}

