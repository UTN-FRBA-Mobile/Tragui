package com.vasco.tragui.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.vasco.tragui.R
import com.vasco.tragui.ui.theme.pixelfyFontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.vasco.tragui.ui.animations.Animations.animationButom
import com.vasco.tragui.ui.tabs.DrinkCabinetTab


class HomeFirstTimeScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalTabNavigator.current

        HomeFirstTime(navigator = navigator)
    }
}


@Composable
fun HomeFirstTime(navigator: TabNavigator) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val displayMetrics = context.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    Column (modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.primary_light))
    ) {
        Box (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)) {
            Column (modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Welcome to",
                    fontFamily = pixelfyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "TRAGUI",
                    fontFamily = pixelfyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 70.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .offset(y = (-10).dp)
                        .fillMaxWidth()
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Box (
                modifier = Modifier
                    .size(width = (screenWidth).dp, height = (screenWidth - 35).dp)
                    .padding(35.dp, 10.dp)
                    .shadowsPlus(
                        type = ShadowsPlusType.SoftLayer,
                        color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                        spread = (-5).dp,
                        offset = DpOffset(13.dp, 13.dp),
                        radius = 0.dp
                    )
                    .background(Color.White)
                    .border(BorderStroke(6.dp, Color.Black))
                    .align(Alignment.Center)
            ) {
                Image(painter = painterResource(R.drawable.starting_wine), contentDescription = "Wine", modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center))
            }
        }
/*
        Box (modifier = Modifier.fillMaxWidth()){
            Button(
                onClick = { navigator.current = DrinkCabinetTab},
                colors = ButtonColors(
                    containerColor = colorResource(id = R.color.primary_dark),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black,
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .padding(35.dp, 20.dp)
                    .border(BorderStroke(6.dp, Color.Black))
                    .shadowsPlus(
                        type = ShadowsPlusType.SoftLayer,
                        color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                        spread = (-5).dp,
                        offset = DpOffset(13.dp, 13.dp),
                        radius = 0.dp
                    )
                    .fillMaxWidth()
            ) {
                Text(text = "Select your bottles",
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
*/
        /// boton animado
        Box (modifier = Modifier.fillMaxWidth()){

            Button(
                onClick = { },
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
                        color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                        spread = (-5).dp,
                        offset = DpOffset(13.dp, 13.dp),
                        radius = 0.dp
                    )
                    .fillMaxWidth()
                    .animationButom()
            ) {
                Text(text = "Select your bottles",
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



