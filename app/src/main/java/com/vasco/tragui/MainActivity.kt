package com.vasco.tragui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.vasco.tragui.ui.tabs.DrinkCabinetTab
import com.vasco.tragui.ui.tabs.HomeTab
import com.vasco.tragui.ui.tabs.SearchTab
import com.vasco.tragui.ui.theme.TraguiTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraguiTheme {
                Surface (modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TabNavigator(HomeTab) {
                        Scaffold(
                            bottomBar = {
                                NavigationBar (
                                    tonalElevation = 0.dp,
                                    containerColor = colorResource(R.color.purple_200),
                                    modifier = Modifier
                                        .shadow(elevation = 10.dp)
                                ) {
                                    TabNavigationItem(tab = HomeTab)
                                    TabNavigationItem(tab = SearchTab)
                                    TabNavigationItem(tab = DrinkCabinetTab)
                                }
                            }
                        ) {
                            Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())){
                                CurrentTab()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
//        selected = tabNavigator.current == tab,
        selected = false,
        onClick = { tabNavigator.current = tab },
        icon = {
            if (tabNavigator.current == tab) {
                tab.options.icon?.let { painter ->
                    Icon(
                        painter = painter,
                        contentDescription = tab.options.title
                    )
                }
            } else {
                tab.options.icon?.let { painter ->
                    Icon(
                        painter = painter,
                        contentDescription = tab.options.title
                    )
                }
            }
        },
    )
}
