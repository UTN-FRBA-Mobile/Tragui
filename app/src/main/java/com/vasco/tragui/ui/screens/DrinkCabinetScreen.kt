package com.vasco.tragui.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import android.content.res.Resources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import coil.compose.AsyncImage
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.google.firebase.logger.Logger
import com.google.rpc.context.AttributeContext.Resource
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.store.DiskDataStore
import com.vasco.tragui.ui.animations.Animations.GifImage
import com.vasco.tragui.ui.theme.pixelfyFontFamily
import okhttp3.internal.wait

class DrinkCabinetScreen: Screen {
    @Composable
    override fun Content() {

        val logger = Logger.getLogger("VascoLogger")
        val context = LocalContext.current
        val dataStore = DiskDataStore(context)
        val userBottles = dataStore.getSelectedBottles().collectAsState(initial = "[]")
        val navigator = LocalNavigator.currentOrThrow

        var bottles = userBottles.value?.removeSurrounding("[", "]") // Remove the brackets
            ?.split(",") // Split by comma
            ?.map { it.trim() }!!



        logger.info("${bottles[0]}")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.primary_light))
                .verticalScroll(state = rememberScrollState())
        ) {
            Card (
                border = BorderStroke(4.dp, Color.Black),
                shape = RectangleShape,
                colors = CardColors(
                    containerColor = colorResource(id = R.color.primary_dark),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Black,
                ),
                modifier = Modifier
                    .padding(20.dp)
                    .padding(bottom = 1.dp)
                    .fillMaxWidth()
                    .shadowsPlus(
                        type = ShadowsPlusType.SoftLayer,
                        color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                        spread = (-5).dp,
                        offset = DpOffset(15.dp, 15.dp),
                        radius = 0.dp
                    )
                    .background(Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .padding(end = 16.dp),
                        contentAlignment = Alignment.TopEnd // Alínea el contenido a la izquierda
                    ){
                        IconButton(onClick = {
                            navigator.push(DrinkCabinetEditScreen())
                        }){
                            Image(
                                painter = painterResource(id = R.drawable.pencil_edit),
                                contentDescription = "Cabinet",
                                Modifier
                                    .size(35.dp)
                            )
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.cabinet),
                        contentDescription = "Cabinet",
                        Modifier
                            .fillMaxSize()
                            .size(150.dp)
                            .padding(top = 3.dp)
                    )
                    Text(
                        text = "Your drink cabinet",
                        color = Color.White,
                        fontFamily = pixelfyFontFamily,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 1.dp)
                            .padding(bottom = 20.dp)
                            .padding(top = 10.dp)
                    )
                }
            }
            bottles = bottles.filter{ bottle -> bottle != "" }
            if(bottles.size == 0) {
                Text(
                    text = "Your cabinet is empty",
                    fontFamily = pixelfyFontFamily,
                    fontSize = 35.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth()
                )
            }
            bottles.map{bottle ->
                    Box(
                        Modifier
                            .padding(bottom = 18.dp)
                    )
                    {
                        // Row con la imagen y el texto
                        Row(
                            Modifier
                                .padding(start = 20.dp)
                                .padding(bottom = 18.dp)
                        ) {
                            var url = "https://www.thecocktaildb.com/images/ingredients/${bottle}-Medium.png"
                            var tamanio = 65
                            if(bottle== "Fernet Branca"){
                                url = "https://pbs.twimg.com/media/GMtwlBTWUAEAdNz?format=png&name=small"
                            }
                            AsyncImage(
                                model = url,
                                contentDescription = bottle,
                                modifier = Modifier.size(tamanio.dp)
                            )
                            Text(
                                text = bottle,
                                fontFamily = pixelfyFontFamily,
                                fontSize = 35.sp,
                                modifier = Modifier
                                    .padding(top = 14.dp)
                                    .fillMaxWidth(),
                            )
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 20.dp)
                                .height(10.dp)
                                .fillMaxWidth()
                                .shadowsPlus(
                                    type = ShadowsPlusType.SoftLayer,
                                    color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                                    spread = (0).dp,
                                    offset = DpOffset(6.dp, 8.dp),
                                    radius = 0.dp,
                                )
                                .background(Color.Black)
                        ) {}

                }
            }
        }

    }
}