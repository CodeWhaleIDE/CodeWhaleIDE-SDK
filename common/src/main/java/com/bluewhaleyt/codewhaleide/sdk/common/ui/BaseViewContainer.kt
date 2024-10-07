package com.bluewhaleyt.codewhaleide.sdk.common.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiRequestState
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiEffect
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiEvent
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiState
import kotlinx.coroutines.flow.Flow

/**
 * [BaseViewContainer] is a stateful UI wrapper, in which you can easily handle the
 * layout to be displayed according to the [UiRequestState].
 *
 * @param State users should implement a data class implementing [State] for state handling.
 * @param Event users should implement a sealed interface implementing [Event] for events firing.
 * @param Effect users should implement a sealed interface implementing [Effect] for effects launching.
 * @param ErrorData the data which will be passed by [UiRequestState.Error].
 * @param SuccessData the data which will be passed by [UiRequestState.Success].
 * @param modifier the modifier of the container.
 * @param state the state of [State].
 * @param effects the effects of [Effect] of [Flow].
 * @param onEffect the callback of launching effects.
 * @param idleContent the content to be displayed when the state is [UiRequestState.Idle].
 * @param loadingContent the content to be displayed when the state is [UiRequestState.Loading].
 * @param errorContent the content to be displayed when the state is [UiRequestState.Error].
 * @param successContent the content to be displayed when the state is [UiRequestState.Success].
 *
 * @author BlueWhaleYT
 */
@Suppress("Unchecked_Cast")
@Composable
fun <State : UiState, Event : UiEvent, Effect : UiEffect, ErrorData, SuccessData> BaseViewContainer(
    modifier: Modifier = Modifier,
    state: State,
    effects: Flow<Effect>,
    onEffect: (Effect) -> Unit = {},
    idleContent: @Composable (UiRequestState.Idle) -> Unit,
    loadingContent: @Composable (UiRequestState.Loading) -> Unit = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    },
    errorContent: @Composable (UiRequestState.Error<ErrorData>) -> Unit = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = it.data.toString(), color = MaterialTheme.colorScheme.error)
        }
    },
    successContent: @Composable (UiRequestState.Success<SuccessData>) -> Unit
) {
    BaseViewContainer<State, Event, Effect>(
        modifier = modifier,
        state = state,
        effects = effects,
        onEffect = onEffect
    ) { requestState ->
        when (requestState) {
            is UiRequestState.Idle -> idleContent(requestState)
            is UiRequestState.Loading -> loadingContent(requestState)
            is UiRequestState.Error<*> -> (requestState as? UiRequestState.Error<ErrorData>)?.let { errorContent(it) }
            is UiRequestState.Success<*> -> (requestState as? UiRequestState.Success<SuccessData>)?.let { successContent(it) }
            else -> throw IllegalArgumentException("Unsupported UiRequestState for stateful UI wrapper.")
        }
    }
}

/**
 * [BaseViewContainer] is a container which passes [UiRequestState] to determine the layout.
 *
 * @param State users should implement a data class implementing [State] for state handling.
 * @param Event users should implement a sealed interface implementing [Event] for events firing.
 * @param Effect users should implement a sealed interface implementing [Effect] for effects launching.
 * @param modifier the modifier of the container.
 * @param state the state of [State].
 * @param effects the effects of [Effect] of [Flow].
 * @param onEffect the callback of launching effects.
 * @param content the content to be displayed and determined by the [UiRequestState] is passed.
 */
@Composable
fun <State : UiState, Event : UiEvent, Effect : UiEffect> BaseViewContainer(
    modifier: Modifier = Modifier,
    state: State,
    effects: Flow<Effect>,
    onEffect: (Effect) -> Unit = {},
    content: @Composable (UiRequestState) -> Unit
) {
    LaunchedEffect(key1 = effects) {
        effects.collect { effect ->
            onEffect(effect)
        }
    }
    Box(modifier) {
        AnimatedContent(targetState = state.requestState) { targetRequestState ->
            content(targetRequestState)
        }
    }
}