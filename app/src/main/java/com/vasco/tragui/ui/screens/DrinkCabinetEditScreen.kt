package com.vasco.tragui.ui.screens

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vasco.tragui.R
import coil.compose.AsyncImage
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.google.firebase.logger.Logger
import com.vasco.tragui.dataManagment.FirebaseGetter
import com.vasco.tragui.store.DiskDataStore
import com.vasco.tragui.ui.animations.Animations.GifImage
import com.vasco.tragui.ui.theme.pixelfyFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class DrinkCabinetEditScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("MutableCollectionMutableState")
    @Composable
    override fun Content() {

        val logger = Logger.getLogger("DrinkCabinetEdit")
        val context = LocalContext.current
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        val screenHeight = displayMetrics.heightPixels / displayMetrics.density

        logger.info("alto ${screenHeight}")
        logger.info("ancho ${screenWidth}")

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        // ---------------------------------------
        // Async code
        // ---------------------------------------
        val dataStore = DiskDataStore(context)
        val userBottles = dataStore.getSelectedBottles().collectAsState(initial = "[]")

        val navigator = LocalNavigator.currentOrThrow

        var (bottles, setBottles) = remember { mutableStateOf<MutableList<String>?>(null) }
        var (firstBottles, setFirstBottles) = remember { mutableStateOf<MutableList<String>?>(null) }
        var selectedBottles by remember {
            mutableStateOf(emptyList<String>())
        }

        var loading by remember {
            mutableStateOf(true)
        }

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    try {
                        setBottles(FirebaseGetter.getBottles())
                        setFirstBottles(FirebaseGetter.getBottles())
                    } catch (e: Exception) {
                        // handle exception
                    } finally {
                        loading = false
                    }
                }
            }
        }

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

        selectedBottles = userBottles.value?.removeSurrounding("[", "]") // Remove the brackets
            ?.split(",") // Split by comma
            ?.map { it.trim() }!!


        // ---------------------------------------
        // Composable content
        // ---------------------------------------


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.primary_light))
        ) {
            Text (
                text = "What do you have at hand?",
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                fontFamily = pixelfyFontFamily,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 20.dp)
            )

            DockedSearchBar(
                query = searchValue,
                onQueryChange = {
                    searchValue = it
                },
                onSearch = {
                    active = false
                    lastSearchedItems.add(it)
                    val regex = Regex(".*${searchValue}.*", RegexOption.IGNORE_CASE)
                    setBottles(firstBottles?.filter { regex.matches(it) } as MutableList<String>?)
                },
                active = active,
                onActiveChange = {active = it},
                shape = RectangleShape,
                leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search") },
                trailingIcon = { Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = "Clear",
                    modifier = Modifier.clickable {
                        if (searchValue.isNotEmpty()) {
                            searchValue = ""
                            setBottles(firstBottles)
                        } else {
                            active = false
                        }
                    }
                ) },
                placeholder = { Text(text = "search for alcohol...", fontFamily = pixelfyFontFamily) },
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
                    Row (modifier = Modifier
                        .padding(14.dp)
                        .clickable {
                            searchValue = it
                        }) {
                        Icon(imageVector = Icons.Default.History, contentDescription = "History icon", modifier = Modifier.padding(end = 10.dp))
                        Text(text = it, fontFamily = pixelfyFontFamily)
                    }
                }
            }

            Box (modifier = Modifier.fillMaxSize()) {
                if (loading)
                    Box (modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(bottom = 100.dp)) {
                        GifImage(Modifier.height(100.dp))
                    }
                else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(120.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(25.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        bottles?.size?.let {
                            items(it) { bottleIndex ->
                                val bottle = bottles[bottleIndex]
                                val isSelected = selectedBottles.contains(bottle)

                                Card(
                                    border = BorderStroke(
                                        5.dp,
                                        if (isSelected) colorResource(id = R.color.green) else Color.Black
                                    ),
                                    shape = RectangleShape,
                                    colors = CardColors(
                                        containerColor = Color.White,
                                        contentColor = Color.Black,
                                        disabledContentColor = Color.Gray,
                                        disabledContainerColor = Color.Black,
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = if (bottleIndex == bottles.size - 1) 150.dp else 0.dp)
                                        .height(230.dp)
                                        .background(Color.White)
                                        .clickable {
                                            val updatedSelectedBottles = if (isSelected) {
                                                selectedBottles.filter { it != bottle }
                                            } else {
                                                selectedBottles + bottle
                                            }
                                            selectedBottles = updatedSelectedBottles
                                        }
                                        .shadowsPlus(
                                            type = ShadowsPlusType.SoftLayer,
                                            color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                                            spread = (-5).dp,
                                            offset = DpOffset(9.dp, 9.dp),
                                            radius = 0.dp
                                        )
                                        .background(Color.White)

                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(  if (isSelected) Color.Gray.copy(alpha = 0.0f) else Color.Gray.copy(alpha = 0.3f))
                                    )
                                    {
                                        if (!isSelected) {
                                            Text(
                                                text = "Add",
                                                color = Color.Red.copy(alpha = 0.4f),
                                                fontFamily = pixelfyFontFamily,
                                                fontSize = 20.sp,
//                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .align(Alignment.TopStart)
                                                    .padding(start = 5.dp, top = 5.dp)
                                            )
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(vertical = 10.dp)
                                        ) {

                                            if (bottle == "Fernet Branca") {
                                                Image(
                                                    painter = painterResource(id = R.drawable.fernet_branca), // Reemplaza con el ID del drawable
                                                    contentDescription = bottle,
                                                    modifier =  Modifier.size(170.dp)
                                                )
                                            } else {
                                                AsyncImage(
                                                    model = "https://www.thecocktaildb.com/images/ingredients/${bottle}-Medium.png",
                                                    contentDescription = bottle,
                                                    modifier =  Modifier.fillMaxWidth()
                                                )
                                            }


                                            Text(
                                                text = bottle,
                                                color = Color.Black,
                                                fontFamily = pixelfyFontFamily,
                                                fontSize = 18.sp,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 1.dp)
                                                    .align(Alignment.BottomCenter)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }


            Button(onClick = {
                coroutineScope.launch {
                    dataStore.saveBottles(selectedBottles.filter{ bottle -> bottle != "" }.toString())
                }
                navigator.push(DrinkCabinetScreen())
            },
                colors = ButtonColors(
                    containerColor = colorResource(id = R.color.primary_dark),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black,
                ),
                border = BorderStroke(6.dp, Color.Black),
                shape = RectangleShape,
                modifier = Modifier
                    .padding(50.dp, 20.dp)
                    .shadowsPlus(
                        type = ShadowsPlusType.SoftLayer,
                        color = colorResource(id = R.color.black).copy(alpha = 0.75f),
                        spread = (-5).dp,
                        offset = DpOffset(13.dp, 13.dp),
                        radius = 0.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
//                    .animationButom()
            )
            {
                Text(text = "SAVE",
                    color = Color.White,
                    fontFamily = pixelfyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )
            }
            }
        }
    }
}
