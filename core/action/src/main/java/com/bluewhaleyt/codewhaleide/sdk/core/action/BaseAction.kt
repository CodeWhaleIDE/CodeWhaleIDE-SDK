package com.bluewhaleyt.codewhaleide.sdk.core.action

import androidx.annotation.CallSuper
import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataContext
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.Presentation
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationStateManager
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus

abstract class BaseAction <T : ActionPresentation>(
    id: String,
    presentation: T
) : Action {
    private val stateManager: PresentationStateManager by lazy { PresentationStateManager.instance }
    final override val presentationState = stateManager.getPresentationState(id, presentation)

    protected val presentation
        get() = presentationState.value

    final override val scope: CoroutineScope
        get() = super.scope + CoroutineExceptionHandler { _, e ->
            e.printStackTrace()
        }

    fun updatePresentation(update: (T) -> T) {
        stateManager.updatePresentation(id, update)
    }
}