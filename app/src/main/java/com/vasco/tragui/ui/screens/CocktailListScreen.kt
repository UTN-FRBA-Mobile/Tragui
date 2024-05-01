package com.vasco.tragui.ui.screens;

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vasco.tragui.R

class CocktailListScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column (modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary_light))) {
            Text(text = "List screen")
            Button(onClick = { navigator.push(CocktailDetailScreen()) }) {
                Text(text = "Go to detail")
            }
            Image(painter = painterResource(id = R.drawable.vino), contentDescription = "Wine", modifier = Modifier.fillMaxSize())
        }
    }
}
