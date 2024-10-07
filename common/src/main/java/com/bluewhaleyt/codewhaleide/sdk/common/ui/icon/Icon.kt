package com.bluewhaleyt.codewhaleide.sdk.common.ui.icon

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.sdk.common.ui.rememberDrawablePainter

sealed interface Icon {
    var disableTint: Boolean

    class VectorIcon @JvmOverloads internal constructor(
        val imageVector: ImageVector,
        override var disableTint: Boolean = false
    ) : Icon
    class PainterIcon @JvmOverloads internal constructor(
        val painter: Painter,
        override var disableTint: Boolean = true
    ) : Icon
    class BitmapIcon @JvmOverloads internal constructor(
        val bitmap: ImageBitmap,
        override var disableTint: Boolean = true
    ) : Icon
    class DrawableIcon @JvmOverloads internal constructor(
        val drawable: Drawable,
        override var disableTint: Boolean = true
    ) : Icon
}

fun ImageVector.toIcon() = Icon.VectorIcon(this)
fun Painter.toIcon() = Icon.PainterIcon(this)
fun ImageBitmap.toIcon() = Icon.BitmapIcon(this)
fun Bitmap.toIcon() = Icon.BitmapIcon(this.asImageBitmap())
fun Drawable.toIcon() = Icon.DrawableIcon(this)

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    icon: Icon?,
    contentDescription: String?,
    tint: Color = LocalContentColor.current,
    remainSpaceForNull: Dp = Dp.Unspecified
) {
    if (icon != null) {
        val finalTint = if (icon.disableTint) Color.Unspecified else tint
        IconImpl(modifier, icon, contentDescription, finalTint)
    } else if (remainSpaceForNull != Dp.Unspecified) {
        Spacer(modifier = modifier.size(remainSpaceForNull))
    }
}

@Composable
private fun IconImpl(
    modifier: Modifier = Modifier,
    icon: Icon,
    contentDescription: String?,
    tint: Color
) {
    when (icon) {
        is Icon.VectorIcon -> {
            Icon(
                modifier = modifier,
                imageVector = icon.imageVector,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is Icon.PainterIcon -> {
            Icon(
                modifier = modifier,
                painter = icon.painter,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is Icon.BitmapIcon -> {
            Icon(
                modifier = modifier,
                bitmap = icon.bitmap,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is Icon.DrawableIcon -> {
            val drawablePainter = rememberDrawablePainter(drawable = icon.drawable)
            Icon(
                modifier = modifier.sizeIn(maxWidth = 24.dp),
                painter = drawablePainter,
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}