package com.bluewhaleyt.codewhaleide.sdk.core.action.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.ui.PresentationIcon
import com.bluewhaleyt.codewhaleide.sdk.core.action.PageAction

@ExperimentalMaterial3Api
@Composable
internal fun ActionActivityBar(
    modifier: Modifier = Modifier,
    actions: List<PageAction>,
    selectedAction: PageAction,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    footer: @Composable (ColumnScope.() -> Unit)? = null,
    onActionSelected: (PageAction) -> Unit
) {
    NavigationRail(
        modifier = modifier,
        header = header
    ) {
        LazyColumn(Modifier.weight(1f)) {
            itemsIndexed(actions, { i, _ -> i }) { _, action ->
                ActionActivityBarItem(
                    action = action,
                    selected = selectedAction == action,
                    onSelected = { onActionSelected(action) }
                )
            }
        }
        footer?.invoke(this)
    }
}

@ExperimentalMaterial3Api
@Composable
private fun ActionActivityBarItem(
    modifier: Modifier = Modifier,
    action: PageAction,
    selected: Boolean,
    onSelected: () -> Unit
) {
    ActionBoxWithTooltip(action = action) { presentation, onPerform ->
        NavigationRailItem(
            modifier = modifier.scale(0.8f),
            enabled = presentation.info.isEnabledAndNotLoading,
            selected = selected,
            icon = { PresentationIcon(presentation = presentation) },
            onClick = {
                onSelected()
                onPerform()
            }
        )
    }
}