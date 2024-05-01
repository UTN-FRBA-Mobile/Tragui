package com.vasco.tragui.ui.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.vasco.tragui.R

object DrinkCabinetTab: Tab {
    private fun readResolve(): Any = HomeTab
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.app_name)
            val icon = painterResource(id = R.drawable.wine_menu)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Column (modifier = Modifier.fillMaxSize()) {
            Text(text = "Drink cabinet", fontSize = 24.sp)
        }
    }
}