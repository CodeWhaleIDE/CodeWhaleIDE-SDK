package com.bluewhaleyt.codewhaleide.sdk.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @author BlueWhaleYT
 */
sealed interface UiRequestState {
    data object Idle : UiRequestState
    data object Loading : UiRequestState
    data class Error<ErrorData>(val data: ErrorData) : UiRequestState
    data class Success<SuccessData>(val data: SuccessData) : UiRequestState
}

interface UiState {
    val requestState: UiRequestState
}

interface UiEvent

interface UiEffect

/**
 * [BaseViewModel] is an abstract implementation from existing [ViewModel], in which
 * MVI architecture is used.
 *
 * @param State users should implement a data class implementing [State] for state handling.
 * @param Event users should implement a sealed interface implementing [Event] for events firing.
 * @param Effect users should implement a sealed interface implementing [Effect] for effects launching.
 * @param initialState the initial state of [State].
 *
 * @author BlueWhaleYT
 */
abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    private val _effects = Channel<Effect>(capacity = Channel.CONFLATED)
    val effects = _effects.receiveAsFlow()

    init {
        viewModelScope.launch {
            _events.collect { _events.emit(it) }
        }
    }

    abstract fun onEvent(event: Event)

    protected fun updateState(state: (State) -> State) {
        _state.value = state(_state.value)
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }

}