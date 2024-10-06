package com.bluewhaleyt.codewhaleide.sdk.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier

@Composable
fun TopAppBarText(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null
) {
    Column(modifier) {
        title()
        AnimatedVisibility(visible = subtitle != null) {
            subtitle?.let {
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(0.6f),
                    LocalTextStyle provides MaterialTheme.typography.titleSmall
                ) { it() }
            }
        }
    }
}