package com.vasco.tragui.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator

class DrinkCabinetScreen: Screen {
    @Composable
    override fun Content() {
        Text(text = "Hola")
    }
}