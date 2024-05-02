package com.vasco.tragui.ui.screens

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vasco.tragui.R
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.google.firebase.logger.Logger
import com.vasco.tragui.dataManagment.FirebaseGetter
import com.vasco.tragui.store.DiskDataStore
import com.vasco.tragui.ui.theme.pixelfyFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DrinkCabinetScreen: Screen {

    val coroutineScope = CoroutineScope(Dispatchers.Main)

    @SuppressLint("MutableCollectionMutableState")
    @Composable
    override fun Content() {

        val logger = Logger.getLogger("VascoLogger")


        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val dataStore = DiskDataStore(context)
        val name = dataStore.getSelectedBottles().collectAsState(initial = "")

        val navigator = LocalNavigator.currentOrThrow


        var (bottles, setBottles) = remember { mutableStateOf<MutableList<String>?>(null) }
        var selectedBottles by remember {
            mutableStateOf<MutableList<String>>(mutableListOf())
        }


        var input by remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.primary_light))
        ) {
//            Text(text = "Cabinet", fontSize = 20.sp)
//            OutlinedTextField(value = input, onValueChange = { input = it })
            Button(onClick = {
                scope.launch {
                    dataStore.saveBottles(input)
                }
                coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        try {
                            bottles = FirebaseGetter.getBottles() // <-- non-suspend blocking method
                            setBottles(FirebaseGetter.getBottles())
                        } catch (e: Exception) {
                            // handle exception
                        } finally {
//                            someState.endProgress()
                        }
                    }
                }
            }) {
                Text(text = "Load")
            }

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
                                    if (selectedBottles.contains(bottles!!.get(bottleIndex)))
                                        Image(
                                            painter = painterResource(id = R.drawable.remove),
                                            contentDescription = "add ${bottles!!.get(bottleIndex)}",
                                            modifier = Modifier
                                                .width(40.dp)
                                                .height(40.dp)
                                                .offset(x = 73.dp, y = (-15).dp)
                                                .clickable {
                                                    val currentBottle = bottles!![bottleIndex]
                                                    if (selectedBottles.contains(currentBottle)) {
                                                        selectedBottles.remove(currentBottle)
                                                    } else {
                                                        selectedBottles.add(currentBottle)
                                                    }
                                                    selectedBottles.toString()
                                                }
                                        )
                                    else
                                        Image(
                                            painter = painterResource(id = R.drawable.add),
                                            contentDescription = "add ${bottles!!.get(bottleIndex)}",
                                            modifier = Modifier
                                                .width(40.dp)
                                                .height(40.dp)
                                                .offset(x = 73.dp, y = (-15).dp)
                                                .clickable {
                                                    val currentBottle = bottles!![bottleIndex]
                                                    if (selectedBottles.contains(currentBottle)) {
                                                        selectedBottles.remove(currentBottle)
                                                    } else {
                                                        selectedBottles.add(currentBottle)
                                                    }
                                                    logger.info(selectedBottles.toString())
                                                }
                                        )
                    }
                }
            }
        }
    }
}