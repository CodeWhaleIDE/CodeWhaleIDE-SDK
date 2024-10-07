package com.bluewhaleyt.codewhaleide.sdk.core.action.presentation

import com.bluewhaleyt.codehaleide.sdk.core.common.presentation.PresentationInfo
import com.bluewhaleyt.codewhaleide.sdk.common.ui.Content
import com.bluewhaleyt.codewhaleide.sdk.common.ui.icon.Icon
import com.bluewhaleyt.codewhaleide.sdk.core.action.Action

data class PageActionPresentation(
    override val info: PresentationInfo,
    override val performCount: Int = 0,
    val pageContent: PageContent
) : ActionPresentation {
    override val showAsAction = ActionPresentation.ShowAsAction.Unspecified

    data class Page @JvmOverloads constructor(
        val title: String,
        val subtitle: String? = null,
        val icon: Icon? = null,
        val actions: List<Action> = emptyList(),
        val content: Content
    )

    sealed interface PageContent {
        data class Single(
            val page: Page
        ) : PageContent

        data class Multiple(
            val title: String,
            val pages: List<Page>
        ) : PageContent

        fun asSingle() = this as PageContent.Single
        fun asMultiple() = this as PageContent.Multiple
    }
}