package com.vasco.tragui.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.vasco.tragui.ui.animations.Animations.animationButom
import com.vasco.tragui.ui.theme.pixelfyFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class DrinkCabinetEditScreen: Screen {

    val coroutineScope = CoroutineScope(Dispatchers.Main)

    @SuppressLint("MutableCollectionMutableState")
    @Composable
    override fun Content() {
        val logger = Logger.getLogger("VascoLogger")

        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val dataStore = DiskDataStore(context)
        val userBottles = dataStore.getSelectedBottles().collectAsState(initial = "")

        val navigator = LocalNavigator.currentOrThrow
        var expanded by remember { mutableStateOf(false) } // para la animaciones


        var (bottles, setBottles) = remember { mutableStateOf<MutableList<String>?>(null) }
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
                        bottles = FirebaseGetter.getBottles() // <-- non-suspend blocking method
                        setBottles(FirebaseGetter.getBottles())
                    } catch (e: Exception) {
                        // handle exception
                    } finally {
//                        userBottles.value.
//                        selectedBottles + currentBottle
//                        selectedBottles
                        loading = false
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.primary_light))
        ) {
//            (if (userBottles != null) userBottles.value else selectedBottles.toString())?.let { Text(text = it, fontSize = 20.sp) }
            Text (
                text = "What do you have at hand?",
                textAlign = TextAlign.Center,
                lineHeight = 50.sp,
                fontFamily = pixelfyFontFamily,
                fontSize = 40.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 20.dp)
            )

            Box (modifier = Modifier.fillMaxSize()) {
                if (loading)
                    Box (modifier = Modifier.fillMaxWidth().align(Alignment.Center)) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(150.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(25.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        bottles?.size?.let {
                            items(it) { bottleIndex ->

                                Card(
                                    border = BorderStroke(4.dp, Color.Black),
                                    shape = RectangleShape,
                                    colors = CardColors(
                                        containerColor = Color.White,
                                        contentColor = Color.Black,
                                        disabledContentColor = Color.Gray,
                                        disabledContainerColor = Color.Black,
                                    ),
                                    modifier = Modifier
                                        .height(200.dp)
                                        .shadowsPlus(
                                            type = ShadowsPlusType.SoftLayer,
                                            color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                                            spread = (-5).dp,
                                            offset = DpOffset(9.dp, 9.dp),
                                            radius = 0.dp
                                        )
                                        .background(Color.White)
                                ) {
                                    Box (modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = 10.dp)) {
                                        AsyncImage(
                                            model = "https://www.thecocktaildb.com/images/ingredients/${bottles!!.get(bottleIndex)}-Medium.png",
                                            contentDescription = bottles!!.get(bottleIndex),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .align(Alignment.TopCenter)
                                        )
                                        Text(
                                            text = bottles!!.get(bottleIndex),
                                            color = Color.Black,
                                            fontFamily = pixelfyFontFamily,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .align(Alignment.BottomCenter)
                                                .fillMaxWidth()
                                                .padding(horizontal = 1.dp)
                                        )
                                    }
                                }
                                IconButton(onClick = {
                                    val currentBottle = bottles!![bottleIndex]
                                    val updatedSelectedBottles =
                                        if (selectedBottles.contains(currentBottle)) {
                                            selectedBottles.filter { it != currentBottle }
                                        } else {
                                            selectedBottles + currentBottle
                                        }

                                    selectedBottles = updatedSelectedBottles
                                    logger.info(selectedBottles.toString())
                                }, modifier = Modifier
                                    .offset(x = 73.dp, y = (-15).dp)
//                            .background(Color.Black) TODO
                                ) {
                                    Image(
                                        painter = painterResource(id = if (selectedBottles.contains(bottles!!.get(bottleIndex))) R.drawable.remove else R.drawable.add),
                                        contentDescription = "add ${bottles!!.get(bottleIndex)}",
                                    )
                                }
                            }
                        }
                    }

                }


            Button(onClick = {
                scope.launch {
                    dataStore.saveBottles(selectedBottles.toString())
                }
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
                    .padding(35.dp, 20.dp)
                    .shadowsPlus(
                        type = ShadowsPlusType.SoftLayer,
                        color = colorResource(id = R.color.black).copy(alpha = 0.75f),
                        spread = (-5).dp,
                        offset = DpOffset(13.dp, 13.dp),
                        radius = 0.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .animationButom()
            )
            {
                Text(text = "SAVE",
                    color = Color.White,
                    fontFamily = pixelfyFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 13.dp)
                        .fillMaxWidth()
                )
            }
            }
        }
    }
}
