package com.bluewhaleyt.codewhaleide.sdk.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalPagingIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPageIndex: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(pageCount){
            Spacer(modifier = Modifier.size(2.5.dp))
            Box(modifier = Modifier
                .height(14.dp)
                .width(width = if (it == currentPageIndex) 32.dp else 14.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = if(it == currentPageIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer)
            )
            Spacer(modifier = Modifier.size(2.5.dp))

        }
    }
}