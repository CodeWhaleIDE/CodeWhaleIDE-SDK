package com.bluewhaleyt.codehaleide.sdk.core.common.presentation.ui

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
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.Presentation
import com.bluewhaleyt.codewhaleide.sdk.common.ui.animation.AnimatedVisibilityNullable

@ExperimentalMaterial3Api
@Composable
fun TooltipScope.PresentationTooltip(
    modifier: Modifier = Modifier,
    presentation: Presentation,
    id: String
) {
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