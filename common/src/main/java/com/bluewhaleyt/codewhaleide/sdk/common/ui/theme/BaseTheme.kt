package com.bluewhaleyt.codewhaleide.sdk.common.ui.theme

import android.graphics.Typeface
import android.os.Build
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bluewhaleyt.codewhaleide.sdk.common.utils.findActivity

private val fontFamily
    @Composable get() = run {
        val context = LocalContext.current
        FontFamily(Typeface.createFromAsset(context.assets, "font/product-sans.ttf"))
    }

@Composable
fun BaseTheme(
    edgeToEdge: Boolean = true,
    systemBarEnabled: Boolean = false,
    appearanceLightSystemBar: Boolean = !isSystemInDarkTheme(),
    typography: Typography = Typography(fontFamily),
    shapes: Shapes = MaterialTheme.shapes,
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = run {
        val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        val colorScheme = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> darkColorScheme()
            else -> lightColorScheme()
        }
        colorScheme
    }
    BaseTheme(
        edgeToEdge = edgeToEdge,
        systemBarEnabled = systemBarEnabled,
        appearanceLightSystemBar = appearanceLightSystemBar,
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@Composable
fun BaseTheme(
    edgeToEdge: Boolean = true,
    systemBarEnabled: Boolean = false,
    appearanceLightSystemBar: Boolean = !isSystemInDarkTheme(),
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: Typography = Typography(fontFamily),
    shapes: Shapes = MaterialTheme.shapes,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current
    val window = context.findActivity().window
    val windowInsetsController = WindowInsetsControllerCompat(window, view)

    if (!view.isInEditMode) {
        SideEffect {
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            windowInsetsController.isAppearanceLightStatusBars = appearanceLightSystemBar
            windowInsetsController.isAppearanceLightNavigationBars = appearanceLightSystemBar

            if (edgeToEdge) {
                context.findActivity().enableEdgeToEdge()
            }

            if (systemBarEnabled) {
                windowInsetsController.apply {
                    show(WindowInsetsCompat.Type.systemBars())
                }
            } else {
                windowInsetsController.apply {
                    hide(WindowInsetsCompat.Type.systemBars())
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

private val defaultTypography = Typography()
fun Typography(fontFamily: FontFamily) = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = fontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = fontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = fontFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = fontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = fontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = fontFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = fontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = fontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = fontFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = fontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = fontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = fontFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = fontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = fontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = fontFamily)
)