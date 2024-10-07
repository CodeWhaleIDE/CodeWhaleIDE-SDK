package com.bluewhaleyt.codewhaleide.sdk.testapp.action

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.toIcon
import com.bluewhaleyt.codewhaleide.sdk.common.ui.setComposeContent
import com.bluewhaleyt.codewhaleide.sdk.core.action.PageAction
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.ActionPresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.core.action.presentation.PageActionPresentation

class MyPageAction1 : PageAction(
    id = "myPageAction1",
    presentation = PageActionPresentation(
        info = ActionPresentationInfo("My Page Action 1", icon = Icons.Outlined.Folder.toIcon()),
        pageContent = PageActionPresentation.PageContent.Single(
            page = PageActionPresentation.Page(
                title = "My Page Action 1 title",
                content = setComposeContent {
                    Column {
                        Button(onClick = {}) {
                            Text("hello button")
                        }
                    }
                }
            )
        )
    )
)