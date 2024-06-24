package com.vasco.tragui.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.vasco.tragui.R

@Composable
fun PixelImageContainer(src: String, height: Dp, width: Dp) {
    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .shadowsPlus(
                type = ShadowsPlusType.SoftLayer,
                color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                spread = (-5).dp,
                offset = DpOffset(9.dp, 9.dp),
                radius = 0.dp
            )
    ) {
        AsyncImage(model = src, contentDescription = "Cocktail thumbnail", modifier = Modifier
            .height(height - 3.9.dp)
            .width(width - 3.9.dp)
            .padding(start = 4.dp, top = 4.dp)
        )
        Image(painter = painterResource(id = R.drawable.pixel_image_container), contentDescription = "Pixel border", modifier = Modifier
            .height(height)
            .width(width)
        )
    }
}

@Preview
@Composable
fun PixelImageContainerPreview() {
    Box(
       modifier = Modifier
           .fillMaxSize()
           .background(color = colorResource(id = R.color.white))
    ) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            PixelImageContainer(src = "https://www.thecocktaildb.com/images/media/drink/u736bd1605907086.jpg/preview", 150.dp, 150.dp)
        }
    }
}