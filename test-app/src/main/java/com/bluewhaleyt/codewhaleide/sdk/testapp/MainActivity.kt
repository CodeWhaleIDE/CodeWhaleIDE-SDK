package com.bluewhaleyt.codewhaleide.sdk.testapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataContext
import com.bluewhaleyt.codehaleide.sdk.core.common.data.DataKeys
import com.bluewhaleyt.codehaleide.sdk.core.common.data.LocalDataContext
import com.bluewhaleyt.codewhaleide.sdk.common.activity.BaseActivity

class MainActivity : BaseActivity() {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val dataContext = DataContext().apply {
            put(DataKeys.Context, context)
        }
        CompositionLocalProvider(LocalDataContext provides dataContext) {
            MainScreen()
        }
    }

}