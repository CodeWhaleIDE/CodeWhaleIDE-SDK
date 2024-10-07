package com.bluewhaleyt.codewhaleide.sdk.core.action.presentation

import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon
import org.jetbrains.annotations.Nls

data class ActionPresentationInfo @JvmOverloads constructor(
    val label: String,
    val summary: String? = null,
    val description: String? = null,
    val icon: Icon? = null,
    val isVisible: Boolean = true,
    val isEnabled: Boolean = isVisible,
    val isLoading: Boolean = false
) : PresentationInfo {
    val isEnabledAndNotLoading
        get() = isEnabled && !isLoading
}