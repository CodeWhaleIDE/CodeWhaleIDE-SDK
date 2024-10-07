package com.bluewhaleyt.codewhaleide.sdk.core.action

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastFilter
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation

internal fun Action.asBaseAction() = this as BaseAction<*>

@Composable
internal fun List<Action>.filterByPresentation(predicate: (ActionPresentation) -> Boolean) =
    fastFilter { it as BaseAction<*>
        val presentation by it.presentationState.collectAsState()
        predicate(presentation)
    }

//@Composable
//internal fun List<Action>.filterByPresentationPlace(vararg places: ActionPlace) =
//    filterByPresentation { presentation ->
//        places.any { it in presentation.places }
//    }