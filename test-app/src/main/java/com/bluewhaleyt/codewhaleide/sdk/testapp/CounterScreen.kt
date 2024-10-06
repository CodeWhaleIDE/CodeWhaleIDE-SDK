package com.bluewhaleyt.codewhaleide.sdk.testapp

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.BaseViewModel
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiRequestState
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiEffect
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiEvent
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiState
import com.bluewhaleyt.codewhaleide.sdk.common.ui.BaseViewContainer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun CounterScreen() {
    val viewModel = viewModel<CounterViewModel>()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    BaseViewContainer<UiState, UiEvent, UiEffect, String, Int>(
        state = state,
        effects = viewModel.effects,
        onEffect = { effect ->
            when (effect) {
                is CounterUiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        },
        idleContent = {
            Column {
                Text("請點擊按鈕以開始加載計數器")
                Button(onClick = { viewModel.onEvent(CounterUiEvent.LoadCounter) }) {
                    Text(text = "加載計數器")
                }
            }
        },
        successContent = {
            Column {
                Text("計數器：${state.counter}")
                Row {
                    Button(onClick = { viewModel.onEvent(CounterUiEvent.Increment) }) {
                        Text("增加")
                    }
                    Button(onClick = { viewModel.onEvent(CounterUiEvent.Decrement) }) {
                        Text("減少")
                    }
                }
            }
        }
    )
}

data class CounterUiState(
    override val requestState: UiRequestState = UiRequestState.Idle,
    val counter: Int = 0,
) : UiState

sealed interface CounterUiEvent : UiEvent {
    data object Increment : CounterUiEvent
    data object Decrement : CounterUiEvent
    data object LoadCounter : CounterUiEvent
}
sealed interface CounterUiEffect : UiEffect {
    data class ShowToast(val message: String) : CounterUiEffect
}

class CounterViewModel : BaseViewModel<CounterUiState, CounterUiEvent, CounterUiEffect>(
    initialState = CounterUiState()
) {

    private fun loadCounter() {
        updateState { it.copy(requestState = UiRequestState.Loading) }
        viewModelScope.launch {
            delay(2.seconds)
            val initialCounter = 0
            updateState { it.copy(counter = initialCounter, requestState = UiRequestState.Success(initialCounter)) }
        }
    }

    override fun onEvent(event: CounterUiEvent) {
        when (event) {
            is CounterUiEvent.Increment -> {
                val newCounter = state.value.counter + 1
                if (newCounter >= 10) {
                    sendEffect(CounterUiEffect.ShowToast("你完成了！"))
//                    updateState { it.copy(counter = 0) }
                    updateState { it.copy(requestState = UiRequestState.Error("failed to reset to 0")) }
                } else {
                    updateState { it.copy(counter = newCounter) }
                }
            }
            is CounterUiEvent.Decrement -> {
                updateState { it.copy(counter = state.value.counter - 1) }
            }
            is CounterUiEvent.LoadCounter -> {
                loadCounter()
            }
        }
    }
}