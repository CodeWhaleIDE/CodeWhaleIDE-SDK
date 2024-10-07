package com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation

@Composable
fun ActionPresentationIcon(
    modifier: Modifier = Modifier,
    presentation: ActionPresentation
) {
    AnimatedVisibility(visible = presentation.info.isVisible) {
        Icon(
            modifier = modifier,
            icon = presentation.info.icon,
            contentDescription = presentation.info.label
        )
    }
}