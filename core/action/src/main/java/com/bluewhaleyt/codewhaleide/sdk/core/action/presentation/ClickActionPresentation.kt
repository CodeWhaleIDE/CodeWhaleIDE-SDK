package com.bluewhaleyt.codewhaleide.sdk.core.action.presentation

data class ClickActionPresentation @JvmOverloads constructor(
    override val info: ActionPresentationInfo,
    override val showAsAction: ActionPresentation.ShowAsAction = ActionPresentation.ShowAsAction.Never,
    override val performCount: Int = 0
) : ActionPresentation