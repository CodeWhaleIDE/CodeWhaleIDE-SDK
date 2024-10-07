package com.bluewhaleyt.codewhaleide.sdk.testapp.action

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ToggleOn
import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataContext
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.toIcon
import com.bluewhaleyt.codewhaleide.sdk.core.action.ActionEvent
import com.bluewhaleyt.codewhaleide.sdk.core.action.ClickAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.ToggleAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ClickActionPresentation
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ToggleActionPresentation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MyToggleAction : ToggleAction(
    id = "myToggleAction",
    presentation = ToggleActionPresentation(
        ActionPresentationInfo(
            "My Toggle Action", null, null, Icons.Default.ToggleOn.toIcon()
        ),
        showAsAction = ActionPresentation.ShowAsAction.IfRoom
    )
) {
    override fun onToggle(toggled: Boolean, event: ActionEvent) {
        event.presentation.label = toggled.toString()
        scope.launch {
            event.presentation.isLoading = true
            delay(2.seconds)
            event.presentation.isLoading = false
        }
    }
}