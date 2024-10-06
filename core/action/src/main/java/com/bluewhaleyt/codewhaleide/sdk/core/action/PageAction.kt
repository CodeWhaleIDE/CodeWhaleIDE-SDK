package com.bluewhaleyt.codewhaleide.sdk.core.action

import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.PageActionPresentation

abstract class PageAction(
    override val id: String,
    presentation: PageActionPresentation
) : BaseAction<PageActionPresentation>(id, presentation) {
    final override fun onPerform(event: ActionEvent) = Unit
}