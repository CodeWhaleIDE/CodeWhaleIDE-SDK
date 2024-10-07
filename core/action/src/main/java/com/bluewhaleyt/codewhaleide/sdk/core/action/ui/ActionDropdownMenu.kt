package com.bluewhaleyt.codewhaleide.sdk.core.action.ui

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.PopupProperties
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ui.ActionPresentationIcon
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ui.ActionPresentationText
import com.bluewhaleyt.codewhaleide.sdk.core.action.Action
import com.bluewhaleyt.codewhaleide.sdk.core.action.ActionGroup
import com.bluewhaleyt.codewhaleide.sdk.core.action.ClickAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.ToggleAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ToggleActionPresentation
import me.saket.cascade.CascadeColumnScope
import me.saket.cascade.CascadeDropdownMenu

@ExperimentalMaterial3Api
@Composable
internal fun ActionDropdownMenu(
    modifier: Modifier = Modifier,
    actions: List<Action>,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    MaterialTheme(shapes = Shapes(MaterialTheme.shapes.large)) {
        CascadeDropdownMenu(
            modifier = modifier.heightIn(max = screenHeight.dp / 1.5f),
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(focusable = false),
        ) {
            actions.fastForEach { action ->
                ActionDropdownMenuItem(action = action)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun CascadeColumnScope.ActionDropdownMenuItem(
    modifier: Modifier = Modifier,
    action: Action
) {
    ActionBoxWithTooltip(action = action) { presentation, onPerform ->
        when (action) {
            is ActionGroup -> {
                DropdownMenuItem(
                    modifier = modifier,
                    enabled = presentation.info.isEnabledAndNotLoading,
                    text = { ActionPresentationText(presentation = presentation) },
                    leadingIcon = { ActionPresentationIcon(presentation = presentation) },
                    children = {
                        action.actions.fastForEach { childAction ->
                            ActionDropdownMenuItem(modifier = modifier, action = childAction)
                        }
                    }
                )
            }
            is ClickAction -> {
                DropdownMenuItem(
                    modifier = modifier,
                    enabled = presentation.info.isEnabledAndNotLoading,
                    onClick = onPerform,
                    text = { ActionPresentationText(presentation = presentation) },
                    leadingIcon = { ActionPresentationIcon(presentation = presentation) },
                )
            }
            is ToggleAction -> { presentation as ToggleActionPresentation
                DropdownMenuItem(
                    modifier = modifier,
                    enabled = presentation.info.isEnabledAndNotLoading,
                    onClick = { onPerform() },
                    text = { ActionPresentationText(presentation = presentation) },
                    leadingIcon = { ActionPresentationIcon(presentation = presentation) },
                    trailingIcon = { Checkbox(checked = presentation.isToggled, onCheckedChange = null) }
                )
            }
        }
    }
}