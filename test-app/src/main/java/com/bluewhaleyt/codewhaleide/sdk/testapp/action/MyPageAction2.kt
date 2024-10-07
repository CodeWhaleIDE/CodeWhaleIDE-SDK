package com.bluewhaleyt.codewhaleide.sdk.testapp.action

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataKey
import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataKeys
import com.bluewhaleyt.codehaleide.sdk.core.common.data.require
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.toIcon
import com.bluewhaleyt.codewhaleide.sdk.common.ui.setComposeContent
import com.bluewhaleyt.codewhaleide.sdk.core.action.ActionEvent
import com.bluewhaleyt.codewhaleide.sdk.core.action.PageAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.PageActionPresentation
import kotlinx.serialization.json.JsonNull.content

class MyPageAction2 : PageAction(
    id = "myPageAction2",
    presentation = PageActionPresentation(
        info = PresentationInfo("My Page Action 2", icon = Icons.Outlined.Settings.toIcon()),
        pageContent = PageActionPresentation.PageContent.Multiple(
            title = "My Page Action 2 title",
            pages = listOf(
                PageActionPresentation.Page(
                    title = "Tab 1",
                    icon = Icons.Outlined.Home.toIcon(),
                    content = setComposeContent {
                        Column {
                            Text("tab 1 content")
                        }
                    }
                ),
                PageActionPresentation.Page(
                    title = "Tab 2",
                    icon = Icons.Outlined.Code.toIcon(),
                    content = setComposeContent {
                        Column {
                            Text("tab 2 content")
                        }
                    },
                    actions = listOf(
                        MyAction(),
                        MyToggleAction()
                    )
                ),
                PageActionPresentation.Page(
                    title = "Tab 3",
                    icon = Icons.Outlined.Extension.toIcon(),
                    content = setComposeContent {
                        Column {
                            Text("tab 3 content")
                        }
                    }
                )
            )
        )
    )
)