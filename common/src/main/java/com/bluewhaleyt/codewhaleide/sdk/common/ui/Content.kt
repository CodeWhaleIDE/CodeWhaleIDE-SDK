package com.bluewhaleyt.codewhaleide.sdk.common.ui

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

sealed interface Content {
    data class View @JvmOverloads constructor(
        val view: (Context) -> android.view.View,
        val onReset: () -> Unit = {},
        val onRelease: () -> Unit = {},
        val update: () -> Unit = {},
    ) : Content
    data class Compose(
        val content: @Composable () -> Unit
    ) : Content
}

fun setViewContent(view: (Context) -> View) = Content.View(view)

fun setComposeContent(content: @Composable () -> Unit) = Content.Compose(content)

@Composable
fun Content(
    modifier: Modifier = Modifier,
    content: Content
) {
    val context = LocalContext.current
    when (content) {
        is Content.View -> {
            if (content.view(context) is ComposeView) {
                ComposeView(context).apply {
                    Content.View(content.view)
                }
            } else {
                AndroidView(
                    modifier = modifier,
                    factory = { content.view(context) },
                    onReset = { content.onReset() },
                    onRelease = { content.onRelease() },
                    update = { content.update() }
                )
            }
        }
        is Content.Compose -> {
            Box(modifier) {
                content.content()
            }
        }
    }
}