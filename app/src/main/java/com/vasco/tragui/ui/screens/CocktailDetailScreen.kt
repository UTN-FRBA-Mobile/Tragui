package com.vasco.tragui.ui.screens;

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class CocktailDetailScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Text(text = "Detail screen")
    }
}
