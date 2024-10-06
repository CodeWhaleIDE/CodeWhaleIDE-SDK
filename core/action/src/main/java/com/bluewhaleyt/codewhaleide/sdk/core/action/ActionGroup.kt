package com.bluewhaleyt.codewhaleide.sdk.core.action

import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation

abstract class ActionGroup(
    override val id: String,
    presentation: ActionPresentation
) : BaseAction<ActionPresentation>(id, presentation) {
    abstract val actions: List<Action>

    final override fun onPerform(event: ActionEvent) = Unit
}