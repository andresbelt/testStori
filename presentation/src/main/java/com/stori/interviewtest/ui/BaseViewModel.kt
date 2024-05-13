package com.stori.interviewtest.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<in Event, State> : ViewModel() {

    val _viewState = MutableStateFlow(initialState())
    val viewState: StateFlow<State> = _viewState.asStateFlow()

    fun postEvent(event: Event) {
        manageEvent(event)
    }

    fun cleanState(){
        _viewState.update {
            initialState()
        }
    }

    protected fun setState(state:State){
        _viewState.update {
            state
        }
    }
    protected abstract fun manageEvent(event: Event)
    protected abstract fun initialState(): State
}
