package com.bluewhaleyt.codewhaleide.sdk.core.action.ui

import androidx.annotation.IntRange
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.toIcon
import com.bluewhaleyt.codewhaleide.sdk.core.action.Action
import com.bluewhaleyt.codewhaleide.sdk.core.action.ActionGroup
import com.bluewhaleyt.codewhaleide.sdk.core.action.filterByPresentation
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentation

@ExperimentalMaterial3Api
@Composable
fun ActionOptionMenuRow(
    modifier: Modifier = Modifier,
    actions: List<Action>,
    @IntRange(from = 0, to = 4) maxRoomSize: Int = 0
) {
//    val actions = actions.filterByPresentationPlace(ActionPlace.EditorToolbar)

    require(maxRoomSize <= 4)

    val actionsNever = actions.filterByPresentation {
        it.showAsAction == ActionPresentation.ShowAsAction.Never
    }

    val remainingRoomSize = (maxRoomSize - actionsNever.size).coerceAtLeast(0)

    val actionsIfRoom = actions.filterByPresentation {
        it.showAsAction == ActionPresentation.ShowAsAction.IfRoom
    }.take(remainingRoomSize)

    // if no more rooms available while there are ifRoom items left, put them here
    val actionsNotShown = actions.filterByPresentation {
        it.showAsAction == ActionPresentation.ShowAsAction.IfRoom
    }.drop(remainingRoomSize)

    ActionOptionMenuRow(
        modifier = modifier,
        actionsIfRoom = actionsIfRoom,
        actionsNever = actionsNever + actionsNotShown
    )
}

@ExperimentalMaterial3Api
@Composable
private fun ActionOptionMenuRow(
    modifier: Modifier = Modifier,
    actionsIfRoom: List<Action>,
    actionsNever: List<Action>,
) {
    Row(modifier) {
        LazyRow {
            items(actionsIfRoom, { it.id }) { action ->
                ActionIconButton(action = action)
            }
        }
        ActionGroupOptionMenuItemIconButton(
            actions = actionsNever,
            icon = Icons.Default.MoreVert.toIcon(),
            contentDescription = "More"
        )
    }
}

@ExperimentalMaterial3Api
@Composable
internal fun ActionGroupOptionMenuItemIconButton(
    modifier: Modifier = Modifier,
    actions: List<Action>,
    icon: Icon?,
    contentDescription: String?,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        AnimatedVisibility(visible = actions.isNotEmpty()) {
            IconButton(
                modifier = modifier,
                enabled = enabled,
                onClick = { expanded = true }
            ) {
                Icon(
                    icon = icon,
                    contentDescription = contentDescription
                )
            }
        }
        ActionDropdownMenu(
            actions = actions,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        )
    }
}