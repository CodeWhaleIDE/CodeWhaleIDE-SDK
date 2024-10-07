package com.bluewhaleyt.codewhaleide.sdk.testapp.action

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.toIcon
import com.bluewhaleyt.codewhaleide.sdk.core.action.ClickAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ClickActionPresentation
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.core.action.ActionEvent

class MyAction : ClickAction(
    id = "myAction",
    presentation = ClickActionPresentation(
        PresentationInfo(
            "My Action", "summary", null, Icons.Default.Home.toIcon()
        )
    )
) {
    override fun onClick(event: ActionEvent) {
//        updatePresentation { it.copy(info = it.info.copy(label = "performed")) }
        event.presentation.label = "performed"
        if (event.presentation.label == "performed") {
//            updatePresentation { it.copy(info = it.info.copy(icon = Icons.Default.Check.toIcon())) }
            event.presentation.icon = Icons.Default.Check.toIcon()
        }
//        updatePresentation { it.copy(info = it.info.copy(isEnabled = presentation.performCount <= 3)) }
//        updatePresentationInfo { it.copy(isEnabled = presentation.performCount <= 3) }
        event.presentation.isEnabled = event.presentation.performCount <= 3
    }
}