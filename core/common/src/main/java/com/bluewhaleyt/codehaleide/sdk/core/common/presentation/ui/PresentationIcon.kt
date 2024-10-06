package com.bluewhaleyt.codehaleide.sdk.core.common.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.Presentation
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon

@Composable
fun PresentationIcon(
    modifier: Modifier = Modifier,
    presentation: Presentation
) {
    AnimatedVisibility(visible = presentation.info.isVisible) {
        Icon(
            modifier = modifier,
            icon = presentation.info.icon,
            contentDescription = presentation.info.label
        )
    }
}