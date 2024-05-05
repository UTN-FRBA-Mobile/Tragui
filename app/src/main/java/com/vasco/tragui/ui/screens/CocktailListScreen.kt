package com.vasco.tragui.ui.screens;

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.logger.Logger
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.dataManagment.FirebaseGetter
import com.vasco.tragui.store.DiskDataStore
import com.vasco.tragui.ui.animations.Animations
import com.vasco.tragui.ui.components.PixelCocktailContainer
import com.vasco.tragui.ui.theme.pixelfyFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//LOGICA DE LA PAGINA PRINCIPAL
class CocktailListScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val logger = java.util.logging.Logger.getLogger("CocktailList")


        var (cocktails, setCocktails) = remember { mutableStateOf<MutableList<Cocktail>?>(null) }

        var loading by remember {
            mutableStateOf(true)
        }

        LifecycleEffect(
            onStarted = {
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        try {
                            val dataStore = DiskDataStore(context)
                            val userBottles = dataStore.getSelectedBottles()
                            userBottles.collect{
                                val bottles = it?.removeSurrounding("[", "]") // Remove the brackets
                                    ?.split(",") // Split by comma
                                    ?.map { it.trim() }!!
                                setCocktails(FirebaseGetter.getCocktailsByBottles(bottles))
                                loading = false
                            }
                        } catch (e: Exception) {
                            logger.info(e.message)
                            // handle exception
                        } finally {
                            loading = false
                        }
                    }
                }
            },
            onDisposed = {

            }
        )

        if (loading)
            Box (modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.primary_light))
                .padding(bottom = 70.dp)) {
                Animations.GifImage(
                    Modifier
                        .align(Alignment.Center)
                        .height(300.dp))
            }
        else {
            Column(
                modifier = Modifier
                    .background(colorResource(id = R.color.primary_light))
            ) {
                // este es el titulo
                Text(
                    text = "Possible Drinks",
                    fontFamily = pixelfyFontFamily,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.primary_dark),
                    modifier = Modifier
                        .padding(bottom = 20.dp, top = 25.dp)
                        .align(Alignment.CenterHorizontally)
                )

                LazyColumn(modifier = Modifier.fillMaxSize())
                {
                    cocktails?.size?.let {
                        // aca es donde se crean todas las tarjetas de los tragos de la pagina principal
                        items(cocktails) { cocktail ->
                            Box(
                                modifier = Modifier
                                    .clickable { navigator.push(CocktailDetailScreen(cocktail.id_firebase)) }
                            ) {
                                PixelCocktailContainer(cocktail)
                            }
                        }
                    }
                }


            }

        }
    }
}
