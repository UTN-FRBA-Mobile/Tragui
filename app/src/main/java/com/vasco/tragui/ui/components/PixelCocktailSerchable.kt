package com.vasco.tragui.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.ui.theme.pixelfyFontFamily

//Funcion que crea las tarjetas de los tragos
@Composable
fun PixelCocktailSerchable( cocktail: Cocktail) {
    Box(
        Modifier

            .padding(bottom = 18.dp)
    )
    {
        // Row con la imagen y el texto
        Row(
            Modifier
                .padding(start = 25.dp)
                .padding(bottom = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
        ) {

            PixelImageContainer(src = cocktail.thumbnail, 80.dp, 80.dp)

            Text(
                text = cocktail.name,
                fontFamily = pixelfyFontFamily,
                fontSize = 35.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 26.dp)
                .height(10.dp)
                .fillMaxWidth()
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val paint = Paint().apply {
                    color = Color.Black
                    style = PaintingStyle.Stroke
                    strokeWidth = 5.dp.toPx()
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(100f, 70f), 1f)
                }
                drawLine(
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    color = Color.Black, // Color de la línea
                    strokeWidth = 5.dp.toPx(), // Grosor de la línea
                    pathEffect = paint.pathEffect // Efecto de trazo punteado
                )
            }
        }



    }

}