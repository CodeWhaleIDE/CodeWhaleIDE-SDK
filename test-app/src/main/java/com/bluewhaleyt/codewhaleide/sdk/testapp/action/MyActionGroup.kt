package com.bluewhaleyt.codewhaleide.sdk.testapp.action

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataContext
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.toIcon
import com.bluewhaleyt.codewhaleide.sdk.core.action.Action
import com.bluewhaleyt.codewhaleide.sdk.core.action.ActionGroup
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ClickActionPresentation

class MyActionGroup : ActionGroup(
    id = "myActionGroup",
    presentation = ClickActionPresentation(
        PresentationInfo(
            "My Action Group", null, null, Icons.Default.Groups.toIcon()
        )
    )
) {
    override val actions = listOf(
        MyAction(),
        MyToggleAction()
    )
}