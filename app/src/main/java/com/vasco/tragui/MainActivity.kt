package com.vasco.tragui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
                                BottomAppBar (
                                    tonalElevation = 0.dp,
                                    containerColor = colorResource(R.color.primary),
                                    contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp),
                                    modifier = Modifier
                                        .drawBehind {
                                            val strokeWidth = 35f
                                            val x = size.width

                                            //top line
                                            drawLine(
                                                color = Color.Black,
                                                start = Offset(
                                                    0f,
                                                    0f
                                                ),
                                                end = Offset(x, 0f),
                                                strokeWidth = strokeWidth
                                            )
                                        }
                                        .shadow(100.dp, )
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
        colors = NavigationBarItemColors(
            selectedIconColor = Color.Transparent,
            selectedTextColor = Color.Transparent,
            selectedIndicatorColor = Color.Transparent,
            unselectedIconColor = Color.Unspecified ,
            unselectedTextColor = Color.Transparent,
            disabledIconColor = Color.Transparent,
            disabledTextColor = Color.Transparent
        ),
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
