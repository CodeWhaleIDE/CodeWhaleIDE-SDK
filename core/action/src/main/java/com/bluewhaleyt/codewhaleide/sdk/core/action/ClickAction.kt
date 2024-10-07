package com.bluewhaleyt.codewhaleide.sdk.core.action

import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ClickActionPresentation

abstract class ClickAction(
    override val id: String,
    presentation: ClickActionPresentation
) : BaseAction<ClickActionPresentation>(id, presentation) {
    abstract fun onClick(event: ActionEvent)

    final override fun onPerform(event: ActionEvent) {
        updatePresentation { it.copy(performCount = it.performCount + 1)  }
        onClick(event)
    }
}