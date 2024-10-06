package com.bluewhaleyt.codewhaleide.sdk.core.action.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.ui.PresentationIcon
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon
import com.bluewhaleyt.codewhaleide.sdk.core.action.Action
import com.bluewhaleyt.codewhaleide.sdk.core.action.ActionGroup
import com.bluewhaleyt.codewhaleide.sdk.core.action.ClickAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.ToggleAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ToggleActionPresentation

@ExperimentalMaterial3Api
@Composable
internal fun ActionIconButton(
    modifier: Modifier = Modifier,
    action: Action
) {
    ActionBoxWithTooltip(action = action) { presentation, onPerform ->
        when (action) {
            is ActionGroup -> {
                ActionGroupOptionMenuItemIconButton(
                    modifier = modifier,
                    actions = action.actions,
                    icon = presentation.info.icon,
                    contentDescription = presentation.info.label,
                    enabled = presentation.info.isEnabledAndNotLoading
                )
            }
            is ClickAction -> {
                IconButton(
                    modifier = modifier,
                    enabled = presentation.info.isEnabledAndNotLoading,
                    onClick = onPerform
                ) {
                    PresentationIcon(presentation = presentation)
                }
            }
            is ToggleAction -> { presentation as ToggleActionPresentation
                FilledTonalIconToggleButton(
                    modifier = modifier,
                    enabled = presentation.info.isEnabledAndNotLoading,
                    checked = presentation.isToggled,
                    onCheckedChange = { onPerform() }
                ) {
                    PresentationIcon(presentation = presentation)
                }
            }
        }
    }
}