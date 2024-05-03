package com.vasco.tragui.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.ui.theme.pixelfyFontFamily




val margarita: Cocktail = Cocktail("Margarita", "12345", "", "Classic Cocktail", "", "WISKY", "", listOf("2 oz tequila", "1 oz orange liqueur", "1 oz fresh lemon juice", "Salt for the rim"), "Moisten the rim of a cocktail glass with lime and then with salt. Add all ingredients to a shaker with ice. Shake well and strain into the prepared glass. Enjoy!", listOf("2 oz", "1 oz", "1 oz", "To taste"), "", "https://example.com/margarita.jpg", "Alcoholic")

@Composable
fun PixelCocktailContainer( cocktail: Cocktail ) {

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
            .height(125.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .shadowsPlus(
                type = ShadowsPlusType.SoftLayer,
                color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                spread = (-3).dp,
                offset = DpOffset(9.dp, 9.dp),
                radius = 0.dp
            )
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
            ) {
                AsyncImage(
                    contentDescription = "Cocktail thumbnail",
                    model = "https://www.thecocktaildb.com/images/media/drink/u736bd1605907086.jpg/preview"
                )
                Column (
                    modifier = Modifier.weight(1f)

                ) {
                    Text(
                        text = cocktail.name,
                        color = Color.Black,
                        fontFamily = pixelfyFontFamily,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(top = 2.dp, bottom = 5.dp, start = 10.dp)
                    )
                    Text(
                        text = cocktail.category,
                        color = Color.Black,
                        fontFamily = pixelfyFontFamily,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding( start = 13.dp)

                    )
                    Text(
                        text = cocktail.type,
                        color = Color.Black,
                        fontFamily = pixelfyFontFamily,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding( bottom = 10.dp, start = 13.dp)
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "SVG Icon",
                    modifier = Modifier.padding( end = 12.dp)

                )
            }
        }
    }
}

@Preview
@Composable
fun PixelCocktailContainer() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    ) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            PixelCocktailContainer(margarita)
        }
    }
}

