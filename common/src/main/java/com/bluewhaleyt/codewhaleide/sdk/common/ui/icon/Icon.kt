package com.bluewhaleyt.codewhaleide.sdk.common.ui.icon

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
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
import androidx.core.content.ContextCompat
import com.bluewhaleyt.codewhaleide.sdk.common.ui.rememberDrawablePainter

sealed interface Icon {
    class VectorImage internal constructor(
        val imageVector: ImageVector
    ) : Icon
    class PainterImage internal constructor(
        val painter: Painter
    ) : Icon
    class BitmapImage internal constructor(
        val bitmap: ImageBitmap
    ) : Icon
    class DrawableImage internal constructor(
        val drawable: Drawable
    ) : Icon
}

fun ImageVector.toIcon() = Icon.VectorImage(this)
fun Painter.toIcon() = Icon.PainterImage(this)
fun ImageBitmap.toIcon() = Icon.BitmapImage(this)
fun Bitmap.toIcon() = Icon.BitmapImage(this.asImageBitmap())
fun Drawable.toIcon() = Icon.DrawableImage(this)
fun @receiver:DrawableRes Int.toIcon(context: Context) = ContextCompat.getDrawable(context, this)?.toIcon()

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    icon: Icon?,
    contentDescription: String?,
    tint: Color = LocalContentColor.current,
    remainSpaceForNull: Dp = Dp.Unspecified
) {
    if (icon != null) {
        IconImpl(modifier, icon, contentDescription, tint)
    } else if (remainSpaceForNull != Dp.Unspecified) {
        Spacer(modifier = modifier.size(remainSpaceForNull))
    }
}

@Composable
private fun IconImpl(
    modifier: Modifier = Modifier,
    icon: Icon,
    contentDescription: String?,
    tint: Color = LocalContentColor.current
) {
    when (icon) {
        is Icon.VectorImage -> {
            Icon(
                modifier = modifier,
                imageVector = icon.imageVector,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is Icon.PainterImage -> {
            Icon(
                modifier = modifier,
                painter = icon.painter,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is Icon.BitmapImage -> {
            Icon(
                modifier = modifier,
                bitmap = icon.bitmap,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is Icon.DrawableImage -> {
            val drawablePainter = rememberDrawablePainter(drawable = icon.drawable)
            Icon(
                painter = drawablePainter,
                contentDescription = contentDescription
            )
        }
    }
}