package com.bluewhaleyt.codewhaleide.sdk.common.ui.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val Icons.Filled.MenuCustom: ImageVector
    get() {
        if (_menu != null) {
            return _menu!!
        }
        _menu = materialIcon(name = "Filled.MenuCustom") {
            materialPath {
                moveTo(3.0f, 6.0f)
                horizontalLineToRelative(18.0f)
                verticalLineToRelative(2.0f)
                lineTo(3.0f, 8.0f)
                verticalLineToRelative(-2.0f)
                close()

                moveTo(3.0f, 11.0f)
                horizontalLineToRelative(12.0f)
                verticalLineToRelative(2.0f)
                lineTo(3.0f, 13.0f)
                verticalLineToRelative(-2.0f)
                close()

                moveTo(3.0f, 16.0f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(2.0f)
                lineTo(3.0f, 18.0f)
                verticalLineToRelative(-2.0f)
                close()
            }
        }
        return _menu!!
    }

private var _menu: ImageVector? = null