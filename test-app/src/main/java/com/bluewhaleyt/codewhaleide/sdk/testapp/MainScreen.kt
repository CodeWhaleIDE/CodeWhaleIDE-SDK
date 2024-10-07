package com.bluewhaleyt.codewhaleide.sdk.testapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bluewhaleyt.codewhaleide.sdk.common.ui.BaseViewContainer
import com.bluewhaleyt.codewhaleide.sdk.common.ui.TopAppBarText
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.MenuCustom
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.toIcon
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.BaseViewModel
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiEffect
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiEvent
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiRequestState
import com.bluewhaleyt.codewhaleide.sdk.common.viewmodel.UiState
import com.bluewhaleyt.codewhaleide.sdk.core.action.Action
import com.bluewhaleyt.codewhaleide.sdk.core.action.PageAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.ui.ActionActivityDrawer
import com.bluewhaleyt.codewhaleide.sdk.core.action.ui.ActionOptionMenuRow
import com.bluewhaleyt.codewhaleide.sdk.testapp.action.MyAction
import com.bluewhaleyt.codewhaleide.sdk.testapp.action.MyActionGroup
import com.bluewhaleyt.codewhaleide.sdk.testapp.action.MyJavaAction
import com.bluewhaleyt.codewhaleide.sdk.testapp.action.MyPageAction1
import com.bluewhaleyt.codewhaleide.sdk.testapp.action.MyPageAction2
import com.bluewhaleyt.codewhaleide.sdk.testapp.action.MyToggleAction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel = viewModel<MainViewModel>()
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    BaseViewContainer<MainState, MainEvent, MainEffect>(
        state = state,
        effects = viewModel.effects
    ) {
        ActionActivityDrawer(
            actions = state.actions.filterIsInstance<PageAction>(),
            drawerState = drawerState,
            selectedAction = state.selectedActivityBarAction,
            onActionSelected = { viewModel.onEvent(MainEvent.SelectActivityBarAction(it)) },
            onDismissRequest = { scope.launch { drawerState.close() } }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            TopAppBarText(
                                title = { Text("title") },
                                subtitle = { Text("subtitle") }
                            )
                        },
                        actions = {
                            ActionOptionMenuRow(actions = state.actions)
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    icon = Icons.Filled.MenuCustom.toIcon(),
                                    contentDescription = if (drawerState.isOpen) "Close drawer" else "Open drawer"
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(Modifier.padding(innerPadding)) {
                    Button(onClick = {}) {
                        Text("hello")
                    }
                }
            }
        }
    }
}

data class MainState(
    override val requestState: UiRequestState = UiRequestState.Idle,
    val actions: SnapshotStateList<Action> = mutableStateListOf(),
    val selectedActivityBarAction: PageAction = actions.filterIsInstance<PageAction>().first()
) : UiState

sealed interface MainEvent : UiEvent {
    data class SelectActivityBarAction(val action: PageAction) : MainEvent
}

sealed interface MainEffect : UiEffect

class MainViewModel : BaseViewModel<MainState, MainEvent, MainEffect>(
    initialState = MainState(
        actions = mutableStateListOf<Action>().apply {
            addAll(listOf(
                MyAction(),
                MyAction(),
                MyToggleAction(),
                MyToggleAction(),
                MyActionGroup(),
                MyPageAction1(),
                MyPageAction2(),
                MyJavaAction()
            ))
        }
    )
) {
    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SelectActivityBarAction -> selectActivityBarAction(event.action)
        }
    }

    private fun selectActivityBarAction(action: PageAction) {
        updateState { it.copy(selectedActivityBarAction = action) }
    }
}