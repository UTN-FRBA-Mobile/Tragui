package com.vasco.tragui.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.google.firebase.logger.Logger
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.dataManagment.FirebaseGetter
import com.vasco.tragui.store.DiskDataStore
import com.vasco.tragui.ui.animations.Animations
import com.vasco.tragui.ui.components.PixelCocktailSerchable
import com.vasco.tragui.ui.theme.pixelfyFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("MutableCollectionMutableState")
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val logger = Logger.getLogger("SearchScreen")
        val coroutineScope = CoroutineScope(Dispatchers.Main)

        // ---------------------------------------
        // Async code
        // ---------------------------------------
        var (cocktails, setCocktails) = remember { mutableStateOf<MutableList<Cocktail>?>(null) }
        //var cocktails: List<Cocktail> by remember { mutableStateOf(emptyList()) }
        var loading by remember { mutableStateOf(false) }

        LifecycleEffect(
//            onStarted = { /*...*/ },
            onDisposed = {
                coroutineScope.cancel()
            }
        )
        // ---------------------------------------
        // Search bar code
        // ---------------------------------------
        var searchValue by remember {
            mutableStateOf("")
        }
        var active by remember {
            mutableStateOf(false)
        }
        var lastSearchedItems = remember<SnapshotStateList<String>> {
            mutableStateListOf()
        }


        // ---------------------------------------
        // Composable content
        // ---------------------------------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.primary_light))
        ) {
            // ---------------------------------------
            // Title
            // ---------------------------------------
            Text(
                text = "What drink do you have in mind?",
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                fontFamily = pixelfyFontFamily,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 20.dp)
            )

            // ---------------------------------------
            // Buscador
            // ---------------------------------------

            DockedSearchBar(
                query = searchValue,
                onQueryChange = {
                    searchValue = it
                },
                onSearch = {
                    loading = true
                    active = false
                    lastSearchedItems.add(it)

                    val regex = Regex(searchValue, RegexOption.IGNORE_CASE)
                    logger.info(regex.toString())

                    coroutineScope.launch {
                        withContext(Dispatchers.Main) {
                            try {
                                setCocktails(FirebaseGetter.getCocktailsByName(regex.toString()))

                            } catch (e: Exception) {
                                // handle exception
                            } finally {
                                delay(550)
                                loading = false
                            }
                        }
                    }


                },
                active = active,
                onActiveChange = { active = it },
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "Clear",
                        modifier = Modifier.clickable {
                            if (searchValue.isNotEmpty()) {
                                searchValue = ""
                                //setBottles(firstBottles)
                            } else {
                                active = false
                            }
                        }
                    )
                },
                placeholder = {
                    Text(
                        text = "search for a drink...",
                        fontFamily = pixelfyFontFamily
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .border(4.dp, Color.Black)
                    .shadowsPlus(
                        type = ShadowsPlusType.SoftLayer,
                        color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                        spread = (-5).dp,
                        offset = DpOffset(9.dp, 9.dp),
                        radius = 0.dp
                    )
            ) {
                lastSearchedItems.reversed().forEach {
                    Row(modifier = Modifier
                        .padding(14.dp)
                        .clickable {
                            searchValue = it
                        }) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History icon",
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(text = it, fontFamily = pixelfyFontFamily)
                    }
                }
            }


            // Carga La imagen del libro de menu de Drinks
            if(cocktails?.size == 0 || cocktails?.size == null){
                Box(modifier = Modifier.fillMaxWidth()
                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.drinksmenu1),
                        contentDescription = "Cabinet",
                        Modifier
                            .fillMaxSize()
                            .padding(30.dp)
                    )

                }
            }


            Box(modifier = Modifier.fillMaxSize()
                .padding(top = 20.dp)) {
                if (loading)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(bottom = 70.dp)
                    ) {
                        Animations.GifImage(Modifier.height(100.dp))
                    }
                else {
                    LazyColumn() {
                        cocktails?.size?.let {
                            items(it) { cocktailsIndex ->
                                Box(
                                    modifier = Modifier
                                        .clickable { navigator.push(CocktailDetailScreen(cocktails[cocktailsIndex].id_firebase)) }
                                ) {

                                    PixelCocktailSerchable(cocktails[cocktailsIndex])
                                }
                            }

                        }

                    }
                }
            }
        }
    }
}

    /*
@Composable
fun  searchCockctail(name: String): Cocktail?   {

    val coroutineScope = CoroutineScope(Dispatchers.Main)

    var loading by remember {
        mutableStateOf(true)
    }

    var (cocktail, setCocktail) = remember { mutableStateOf<MutableList<Cocktail>?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                try {
                    setCocktail(FirebaseGetter.getCocktailsByName(name))
                } catch (e: Exception) {
                    // handle exception
                } finally {
                    loading = false
                }
            }
        }
    }


}

 */

    @Composable

    fun searchCockctail(name: String): List<Cocktail> {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        var cocktails: List<Cocktail> by remember { mutableStateOf(emptyList()) }
        var loading by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    try {
                        cocktails = FirebaseGetter.getCocktailsByName(name)
                    } catch (e: Exception) {
                        // handle exception
                    } finally {
                        loading = false
                    }
                }
            }
        }

        return cocktails
    }



