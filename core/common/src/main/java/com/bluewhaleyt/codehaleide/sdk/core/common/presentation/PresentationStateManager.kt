package com.bluewhaleyt.codehaleide.sdk.core.common.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
class PresentationStateManager {
    private val presentations = mutableMapOf<String, MutableStateFlow<Any>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Presentation> getPresentationState(id: String, initial: T): StateFlow<T> {
        return presentations.getOrPut(id) { MutableStateFlow(initial) }.asStateFlow() as StateFlow<T>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Presentation> updatePresentation(id: String, update: (T) -> T) {
        val currentFlow = presentations[id] as? MutableStateFlow<T>
        currentFlow?.value = update(currentFlow?.value ?: Presentation.EMPTY as T)
    }

    companion object {
        val instance = PresentationStateManager()
    }
}