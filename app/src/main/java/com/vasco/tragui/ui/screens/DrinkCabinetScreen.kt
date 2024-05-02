package com.vasco.tragui.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.vasco.tragui.R
import com.vasco.tragui.ui.tabs.DrinkCabinetTab
import com.vasco.tragui.ui.theme.pixelfyFontFamily
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay


class DrinkCabinetScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var expanded by remember { mutableStateOf(false) } // para la animaciones

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.white))
        ) {
            Text(text = "Cabinet", fontSize = 20.sp)
        }

        /// boton animado
        //Box (modifier = Modifier.fillMaxWidth()){

         //   PulsateEffect()
        //}

    }
}

//=================== Animacioones =====================//
/*
@Composable
fun PulsateEffect(){

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
            .pulsateClick()
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

fun Modifier.pulsateClick() = composed {

    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(50),
            repeatMode = RepeatMode.Reverse
        )
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { }
}
*/
