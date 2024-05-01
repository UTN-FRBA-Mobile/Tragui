package com.vasco.tragui.ui.screens;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vasco.tragui.R
import com.vasco.tragui.ui.components.PixelImageContainer

class CocktailDetailScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.white))
        ) {
            Text(text = "Detail screen", fontSize = 20.sp)
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                PixelImageContainer(src = "https://www.thecocktaildb.com/images/media/drink/u736bd1605907086.jpg/preview", 150.dp, 150.dp)
            }
        }

    }
}
