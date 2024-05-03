package com.vasco.tragui.ui.screens;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.dataManagment.FirebaseGetter
import com.vasco.tragui.store.DiskDataStore
import com.vasco.tragui.ui.animations.Animations
import com.vasco.tragui.ui.components.PixelImageContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class CocktailDetailScreen(val cocktail_id: String): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        var (cocktail, setCocktail) = remember { mutableStateOf<Cocktail?>(null) }

        var loading by remember {
            mutableStateOf(true)
        }

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    try {
                            setCocktail(FirebaseGetter.getCocktailById(cocktail_id))
                    } catch (e: Exception) {
                        // handle exception
                    } finally {
                        loading = false
                    }
                }
            }
        }

        if (loading)
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp)) {
                Animations.GifImage(Modifier.align(Alignment.Center))
            }
        else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.white))
        ) {
            cocktail?.name?.let { Text(text = it, fontSize = 20.sp) }
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                cocktail?.thumbnail?.let { PixelImageContainer(src = it, 150.dp, 150.dp) }
            }
        }
        }
    }
}
