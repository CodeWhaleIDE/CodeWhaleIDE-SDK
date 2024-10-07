package com.bluewhaleyt.codewhaleide.sdk.core.action.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codehaleide.sdk.core.common.data.LocalDataContext
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.ui.PresentationTooltip
import com.bluewhaleyt.codewhaleide.sdk.core.action.Action
import com.bluewhaleyt.codewhaleide.sdk.core.action.ActionEvent
import com.bluewhaleyt.codewhaleide.sdk.core.action.BaseAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation
import kotlinx.coroutines.launch

private typealias ActionContent = @Composable (presentation: ActionPresentation, onPerform: () -> Unit) -> Unit

@ExperimentalMaterial3Api
@Composable
fun ActionBoxWithTooltip(
    modifier: Modifier = Modifier,
    action: Action,
    content: ActionContent
) {
    ActionBox(
        modifier = modifier,
        action = action
    ) { presentation, onPerform ->
        val tooltipState = rememberTooltipState()
        TooltipBox(
            state = tooltipState,
            positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
            tooltip = { PresentationTooltip(presentation = presentation, id = action.id) },
            content = { content(presentation, onPerform) },
            focusable = false
        )
    }
}

@Composable
fun ActionBox(
    modifier: Modifier = Modifier,
    action: Action,
    content: ActionContent
) {
    BaseActionBox(
        modifier = modifier,
        action = action,
        content = content
    )
}

@Composable
private fun BaseActionBox(
    modifier: Modifier = Modifier,
    action: Action,
    content: ActionContent
) { action as BaseAction<*>
    val event = ActionEvent(
        action = action,
        dataContext = LocalDataContext.current
    )
    val presentation by action.presentationState.collectAsState()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = presentation.info.isVisible) {
            LaunchedEffect(key1 = Unit) {
                action.scope.launch {
                    if (presentation.performCount <= 0) action.onInitialize(event)
                }
            }
            content(presentation) {
                action.scope.launch {
                    action.onPerform(event)
                }
            }
        }
        if (presentation.info.isLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceDim.copy(0.38f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = LocalContentColor.current.copy(0.38f)
                )
            }
        }
    }
}