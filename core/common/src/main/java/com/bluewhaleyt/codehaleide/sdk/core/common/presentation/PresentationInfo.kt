package com.bluewhaleyt.codehaleide.sdk.core.common.presentation

import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon
import org.jetbrains.annotations.Nls

data class PresentationInfo @JvmOverloads constructor(
    @Nls(capitalization = Nls.Capitalization.Title) val label: String,
    @Nls(capitalization = Nls.Capitalization.Title) val summary: String? = null,
    @Nls(capitalization = Nls.Capitalization.Sentence) val description: String? = null,
    val icon: Icon? = null,
    val isVisible: Boolean = true,
    val isEnabled: Boolean = isVisible,
    val isLoading: Boolean = false
) {
    val isEnabledAndNotLoading
        get() = isEnabled && !isLoading
}