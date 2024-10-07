package com.bluewhaleyt.codewhaleide.sdk.core.action.presentation

import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.Presentation
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo
import org.jetbrains.annotations.ApiStatus

interface ActionPresentation : Presentation {
    override val info: ActionPresentationInfo
    val showAsAction: ShowAsAction
    val performCount: Int

    enum class ShowAsAction {
        IfRoom, Never, Unspecified
    }
}