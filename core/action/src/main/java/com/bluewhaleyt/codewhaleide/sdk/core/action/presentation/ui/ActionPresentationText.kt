package com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation

@Composable
fun ActionPresentationText(
    modifier: Modifier = Modifier,
    presentation: ActionPresentation
) {
    AnimatedVisibility(visible = presentation.info.isVisible) {
        Column(modifier) {
            Text(
                text = presentation.info.label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            presentation.info.summary?.let {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall,
                    color = LocalContentColor.current.copy(0.6f)
                )
            }
        }
    }
}