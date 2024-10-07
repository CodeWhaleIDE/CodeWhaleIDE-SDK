package com.bluewhaleyt.codewhaleide.sdk.core.action

import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationStateManager
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ClickActionPresentation
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.PageActionPresentation
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ToggleActionPresentation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus

abstract class BaseAction <T : ActionPresentation>(
    id: String,
    presentation: T
) : Action {
    private val stateManager: PresentationStateManager by lazy { PresentationStateManager.instance }
    internal val presentationState = stateManager.getPresentationState(id, presentation)

    internal val presentation
        get() = presentationState.value

    final override val scope: CoroutineScope
        get() = super.scope + CoroutineExceptionHandler { _, e ->
            e.printStackTrace()
        }

    internal fun updatePresentation(update: (T) -> T) {
        stateManager.updatePresentation(id, update)
    }

    internal fun updatePresentationInfo(update: (ActionPresentationInfo) -> ActionPresentationInfo) {
        val currentPresentation = presentation
        val updatedInfo = update(currentPresentation.info)
        val newPresentation = when (currentPresentation) {
            is ClickActionPresentation -> currentPresentation.copy(info = updatedInfo)
            is ToggleActionPresentation -> currentPresentation.copy(info = updatedInfo)
            is PageActionPresentation -> currentPresentation.copy(info = updatedInfo)
            else -> throw IllegalArgumentException("Unsupported ActionPresentation type")
        }
        stateManager.updatePresentation<ActionPresentation>(id) { newPresentation }
    }
}