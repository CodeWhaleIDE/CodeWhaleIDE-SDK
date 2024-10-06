package com.bluewhaleyt.codewhaleide.sdk.core.action

import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.plus

interface Action {
    val id: String
    val presentationState: StateFlow<ActionPresentation>

    val scope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main.immediate) +
                CoroutineExceptionHandler { _, e -> } +
                CoroutineName("ActionCoroutineScope")

    fun onInitialize(event: ActionEvent) = Unit
    fun onPerform(event: ActionEvent)
}