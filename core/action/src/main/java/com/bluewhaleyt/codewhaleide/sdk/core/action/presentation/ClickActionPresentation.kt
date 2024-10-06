package com.bluewhaleyt.codewhaleide.sdk.core.action.presentation

import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo

data class ClickActionPresentation @JvmOverloads constructor(
    override val info: PresentationInfo,
    override val showAsAction: ActionPresentation.ShowAsAction = ActionPresentation.ShowAsAction.Never,
    override val performCount: Int = 0
) : ActionPresentation