package com.vasco.tragui.ui.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.vasco.tragui.R
import com.vasco.tragui.ui.screens.CocktailListScreen
import com.vasco.tragui.ui.screens.HomeFirstTimeScreen

object HomeTab: Tab {
    private fun readResolve(): Any = HomeTab
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.app_name)
            val icon = painterResource(id = R.drawable.cocktail_menu)
//            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            HomeFirstTimeScreen()
        )
    }
}