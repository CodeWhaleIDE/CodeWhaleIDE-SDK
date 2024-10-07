package com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.sdk.common.ui.animation.AnimatedVisibilityNullable
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation

@ExperimentalMaterial3Api
@Composable
fun TooltipScope.ActionPresentationTooltip(
    modifier: Modifier = Modifier,
    presentation: ActionPresentation,
    id: String
) {
    AnimatedVisibility(visible = presentation.info.isVisible) {
        RichTooltip(
            modifier = modifier,
            title = { Text(text = presentation.info.label) },
            text = {
                Column {
                    AnimatedVisibilityNullable(target = presentation.info.description) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = it)
                        }
                    }
                    Text(
                        text = "ID: $id",
                        color = LocalContentColor.current.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelLarge.run {
                            copy(
                                fontSize = fontSize * 0.9f,
                                letterSpacing = letterSpacing * 0.9f
                            )
                        }
                    )
                }
            }
        )
    }
}