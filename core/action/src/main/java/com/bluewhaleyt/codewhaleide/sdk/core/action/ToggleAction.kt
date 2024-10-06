package com.bluewhaleyt.codewhaleide.sdk.core.action

import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ToggleActionPresentation

abstract class ToggleAction(
    override val id: String,
    presentation: ToggleActionPresentation
) : BaseAction<ToggleActionPresentation>(id, presentation) {
    abstract fun onToggle(toggled: Boolean, event: ActionEvent)

    final override fun onPerform(event: ActionEvent) {
        updatePresentation { it.copy(performCount = it.performCount + 1, isToggled = !it.isToggled) }
        onToggle(presentation.isToggled, event)
    }
}