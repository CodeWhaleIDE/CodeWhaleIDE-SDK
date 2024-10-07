package com.bluewhaleyt.codewhaleide.sdk.common.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon

sealed interface Image {
    class PainterImage internal constructor(
        val painter: Painter
    ) : Image
    class BitmapImage internal constructor(
        val bitmap: ImageBitmap
    ) : Image
    class DrawableImage internal constructor(
        val drawable: Drawable
    ) : Image
}

fun Painter.toImage() = Image.PainterImage(this)
fun ImageBitmap.toImage() = Image.BitmapImage(this)
fun Bitmap.toImage() = Image.BitmapImage(this.asImageBitmap())
fun Drawable.toImage() = Image.DrawableImage(this)

@Composable
fun Image(
    modifier: Modifier = Modifier,
    image: Image?,
    contentDescription: String?,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    remainSpaceForNull: Dp = Dp.Unspecified
) {
    if (image != null) {
        ImageImpl(modifier, image, contentDescription, alignment, contentScale, alpha, colorFilter, filterQuality)
    } else if (remainSpaceForNull != Dp.Unspecified) {
        Spacer(modifier = modifier.size(remainSpaceForNull))
    }
}

@Composable
private fun ImageImpl(
    modifier: Modifier = Modifier,
    image: Image,
    contentDescription: String?,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality
) {
    when (image) {
        is Image.PainterImage -> {
            androidx.compose.foundation.Image(
                modifier = modifier,
                painter = image.painter,
                contentDescription = contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }
        is Image.BitmapImage -> {
            androidx.compose.foundation.Image(
                modifier = modifier,
                bitmap = image.bitmap,
                contentDescription = contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                filterQuality = filterQuality
            )
        }
        is Image.DrawableImage -> {
            val drawablePainter = rememberDrawablePainter(drawable = image.drawable)
            androidx.compose.foundation.Image(
                modifier = modifier,
                painter = drawablePainter,
                contentDescription = contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }
    }
}