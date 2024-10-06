package com.bluewhaleyt.codewhaleide.sdk.core.action.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.bluewhaleyt.codewhaleide.sdk.common.ui.Content
import com.bluewhaleyt.codewhaleide.sdk.common.ui.TopAppBarText
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.toIcon
import com.bluewhaleyt.codewhaleide.sdk.core.action.Action
import com.bluewhaleyt.codewhaleide.sdk.core.action.PageAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.PageActionPresentation
import kotlinx.serialization.json.JsonNull.content

@ExperimentalMaterial3Api
@Composable
fun ActionActivityDrawer(
    modifier: Modifier = Modifier,
    actions: List<PageAction>,
    drawerState: DrawerState,
    selectedAction: PageAction,
    gestureEnabled: Boolean = drawerState.isOpen,
    onActionSelected: (PageAction) -> Unit,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val verticalPagerState = rememberPagerState(
        pageCount = { actions.size }
    )
    LaunchedEffect(key1 = selectedAction) {
        verticalPagerState.scrollToPage(actions.indexOf(selectedAction))
    }
    ModalNavigationDrawer(
        modifier = modifier,
//            scrimColor = Color.Transparent,
        gesturesEnabled = gestureEnabled,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerState = drawerState,
                drawerContainerColor = Color.Transparent
            ) {
                ActionActivityDrawerContent(
                    actions = actions,
                    selectedAction = selectedAction,
                    pagerState = verticalPagerState,
                    onActionSelected = onActionSelected,
                    onDismissRequest = onDismissRequest
                )
            }
        },
        content = content
    )
}

@ExperimentalMaterial3Api
@Composable
private fun ActionActivityDrawerContent(
    modifier: Modifier = Modifier,
    actions: List<PageAction>,
    pagerState: PagerState,
    selectedAction: PageAction,
    onActionSelected: (PageAction) -> Unit,
    onDismissRequest: () -> Unit
) {
    Surface(
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(
            topStart = CornerSize(0.dp),
            topEnd = MaterialTheme.shapes.extraLarge.topEnd,
            bottomStart = CornerSize(0.dp),
            bottomEnd = MaterialTheme.shapes.extraLarge.bottomEnd
        ),
        modifier = modifier.width(360.dp)
    ) {
        Row {
            Surface(
                tonalElevation = 3.dp,
                shape = RoundedCornerShape(
                    topStart = CornerSize(0.dp),
                    topEnd = MaterialTheme.shapes.extraLarge.topEnd,
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = MaterialTheme.shapes.extraLarge.bottomEnd
                ),
                modifier = Modifier
                    .width(56.dp)
                    .fillMaxHeight()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    ActionActivityBar(
                        actions = actions,
                        selectedAction = selectedAction,
                        header = {
                            IconButton(onClick = onDismissRequest) {
                                Icon(
                                    icon = Icons.Default.Close.toIcon(),
                                    contentDescription = "Close"
                                )
                            }
                        },
                        onActionSelected = onActionSelected
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = CornerSize(0.dp),
                            topEnd = MaterialTheme.shapes.extraLarge.topEnd,
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = MaterialTheme.shapes.extraLarge.bottomEnd
                        )
                    )
            ) {
                ActionActivityDrawerPager(
                    actions = actions,
                    state = pagerState
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun ActionActivityDrawerPager(
    modifier: Modifier = Modifier,
    actions: List<Action>,
    state: PagerState
) {
    VerticalPager(
        modifier = modifier,
        state = state,
        beyondViewportPageCount = actions.size,
        userScrollEnabled = false,
        key = { it }
    ) { pageIndex ->
        val action = actions[pageIndex]
        ActionBox(action = action) { presentation, _ -> presentation as PageActionPresentation
            when (presentation.pageContent) {
                is PageActionPresentation.PageContent.Single -> {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    TopAppBarText(
                                        title = { Text(text = presentation.pageContent.page.title) },
                                        subtitle = presentation.pageContent.page.subtitle?.let { { Text(text = it) } }
                                    )
                                },
                                actions = {
                                    ActionOptionMenuRow(actions = presentation.pageContent.page.actions)
                                }
                            )
                        }
                    ) { innerPadding ->
                        Content(
                            modifier = Modifier.padding(innerPadding),
                            content = presentation.pageContent.page.content
                        )
                    }
                }
                is PageActionPresentation.PageContent.Multiple -> {
                    var selectedPagingActionTabIndex by rememberSaveable { mutableIntStateOf(0) }
                    val pagingContentTabbed = presentation.pageContent
                    val pagingActionTabs = pagingContentTabbed.pages
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    TopAppBarText(
                                        title = { Text(text = pagingContentTabbed.title) },
                                        subtitle = pagingActionTabs[selectedPagingActionTabIndex].subtitle?.let { { Text(text = it) } }
                                    )
                                },
                                actions = {
                                    ActionOptionMenuRow(actions = pagingActionTabs[selectedPagingActionTabIndex].actions)
                                }
                            )
                        },
                        bottomBar = {
                            BottomAppBar(
                                modifier = Modifier.clip(RoundedCornerShape(
                                    topStart = MaterialTheme.shapes.extraLarge.topStart,
                                    topEnd = MaterialTheme.shapes.extraLarge.topEnd,
                                    bottomStart = CornerSize(0.dp),
                                    bottomEnd = CornerSize(0.dp)
                                )),
                                containerColor = MaterialTheme.colorScheme.surface,
                                tonalElevation = 3.dp
                            ) {
                                pagingActionTabs.fastForEachIndexed { index, tab ->
                                    NavigationBarItem(
                                        selected = index == selectedPagingActionTabIndex,
                                        onClick = {
                                            selectedPagingActionTabIndex = index
                                        },
                                        icon = {
                                            Icon(icon = tab.icon, contentDescription = tab.title)
                                        },
                                        label = {
                                            Text(tab.title)
                                        }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        val horizontalPagerState = rememberPagerState(
                            pageCount = { pagingActionTabs.size }
                        )
                        HorizontalPager(
                            state = horizontalPagerState,
                            beyondViewportPageCount = pagingActionTabs.size,
                            userScrollEnabled = false,
                            key = { it }
                        ) { pageIndex ->
                            Content(
                                modifier = Modifier.padding(innerPadding),
                                content = pagingActionTabs[pageIndex].content
                            )
                        }
                        LaunchedEffect(key1 = selectedPagingActionTabIndex) {
                            horizontalPagerState.scrollToPage(selectedPagingActionTabIndex)
                        }
                    }
                }
            }
        }
    }
}