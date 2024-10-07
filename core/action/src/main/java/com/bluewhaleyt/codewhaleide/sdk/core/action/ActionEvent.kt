package com.bluewhaleyt.codewhaleide.sdk.core.action

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataContext
import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataKeys
import com.bluewhaleyt.codehaleide.sdk.core.common.data.require
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.toIcon
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Nls
import kotlin.coroutines.CoroutineContext

class ActionEvent internal constructor(
    private val action: Action,
    val dataContext: DataContext
) {
    val context
        get() = dataContext.require(DataKeys.Context)

    val presentation = Presentation()

//    @JvmOverloads
//    fun launch(
//        coroutineContext: CoroutineContext = Dispatchers.Main.immediate,
//        block: Runnable
//    ) { action.scope.launch(coroutineContext) { block.run() } }

    inner class Presentation internal constructor(){
        private val presentation
            get() = action.asBaseAction().presentationState.value

        var label = presentation.info.label
            set(value) = action.asBaseAction().updatePresentationInfo { it.copy(label = value) }

        var summary = presentation.info.summary
            set(value) = action.asBaseAction().updatePresentationInfo { it.copy(summary = value) }

        var description = presentation.info.description
            set(value) = action.asBaseAction().updatePresentationInfo { it.copy(description = value) }

        var icon = presentation.info.icon
            set(value) = action.asBaseAction().updatePresentationInfo { it.copy(icon = value) }

        fun setIconResId(@DrawableRes resId: Int) {
            icon = ContextCompat.getDrawable(context, resId)?.toIcon()
        }

        var isVisible = presentation.info.isVisible
            set(value) = action.asBaseAction().updatePresentationInfo { it.copy(isVisible = value) }

        var isEnabled = presentation.info.isEnabled
            set(value) = action.asBaseAction().updatePresentationInfo { it.copy(isEnabled = value) }

        var isLoading = presentation.info.isLoading
            set(value) = action.asBaseAction().updatePresentationInfo { it.copy(isLoading = value) }

        val performCount
            get() = presentation.performCount
    }

}