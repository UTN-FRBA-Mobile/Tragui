package com.vasco.tragui.ui.screens;

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gigamole.composeshadowsplus.common.ShadowsPlusType
import com.gigamole.composeshadowsplus.common.shadowsPlus
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.dataManagment.FirebaseGetter
import com.vasco.tragui.store.DiskDataStore
import com.vasco.tragui.ui.animations.Animations
import com.vasco.tragui.ui.components.PixelCocktailContainer
import com.vasco.tragui.ui.components.PixelImageContainer
import com.vasco.tragui.ui.theme.pixelfyFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class CocktailDetailScreenForSearch(val cocktail_id: String?): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var (cocktail, setCocktail) = remember { mutableStateOf<Cocktail?>(null) }

        var loading by remember {
            mutableStateOf(true)
        }

        LifecycleEffect(
            onStarted = {
                if (cocktail_id == null) {
                    navigator.pop()
                    return@LifecycleEffect
                }
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        try {
                            setCocktail(FirebaseGetter.getCocktailById(cocktail_id))
                        } catch (e: Exception) {
                            // handle exception
                        } finally {
                            loading = false
                        }
                    }
                }
            },
            onDisposed = {}
        )

        if (loading)
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp)) {
                Animations.GifImage(Modifier.align(Alignment.Center))
            }
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.primary_light))
                    .verticalScroll(state = rememberScrollState())
            ){
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
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .padding(top = 15.dp)
                        .padding(bottom = 20.dp)
                        .shadowsPlus(
                            type = ShadowsPlusType.SoftLayer,
                            color = colorResource(id = R.color.black).copy(alpha = 0.25f),
                            spread = (-3).dp,
                            offset = DpOffset(9.dp, 9.dp),
                            radius = 0.dp
                        )
                        .background(Color.White)
                ){

                    Column(
                                modifier = Modifier.padding(horizontal = 15.dp)
                            ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ){
                            IconButton(onClick = {}){
                                Image(
                                    painter = painterResource(id = R.drawable.share),
                                    contentDescription = "Cabinet",
                                    Modifier
                                        .height(38.dp)
                                        .padding(start = 2.dp)
                                )
                            }
                            IconButton(onClick = {navigator.push(SearchScreen())}){
                                Image(
                                    painter = painterResource(id = R.drawable.red_cross),
                                    contentDescription = "Cabinet",
                                    Modifier
                                        .height(30.dp)
                                        .padding(end = 2.dp)
                                )
                            }
                        }
                        if (cocktail != null) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cocktail.name,
                                    color = Color.Black,
                                    fontFamily = pixelfyFontFamily,
                                    fontSize = 45.sp,
                                    textAlign = TextAlign.Center,
                                    lineHeight = (45 * 1.05).sp
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 25.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                PixelImageContainer(src = cocktail.thumbnail, 150.dp, 150.dp)
                            }
                            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 25.dp)){
                                Text(
                                    text = "Glass type",
                                    color = Color.Black,
                                    fontFamily = pixelfyFontFamily,
                                    fontSize = 30.sp,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 70.sp
                                )
                                Image(painter = getGlassPainterByGlassType(glassType = cocktail.glassType), contentDescription = "vaso", Modifier.size(70.dp))
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp)
                                    .padding(top = 25.dp, bottom = 5.dp)
                            ) {
                                Text(
                                    text = AnnotatedString.Builder().apply {
                                        append("Ingredients:")
                                        addStyle(
                                            style = SpanStyle(textDecoration = TextDecoration.Underline),
                                            start = 0,
                                            end = "Ingredients:".length
                                        )
                                    }.toAnnotatedString(),
                                    color = Color.Black,
                                    fontFamily = pixelfyFontFamily,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            var index = 0
                            cocktail.ingredients.map { ingredient ->
                                var texto = ingredient
                                if(cocktail.measures.size > index){ texto = cocktail.measures[index]+" "+texto}
                                texto = "- "+texto
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp)){
                                Text(
                                    text = texto,
                                    modifier = Modifier.padding(bottom = 4.dp),
                                    color = Color.Black,
                                    fontFamily = pixelfyFontFamily,
                                )}
                                index++
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp)
                                    .padding(bottom = 10.dp, top = 20.dp)
                            ) {
                                Text(
                                    text = AnnotatedString.Builder().apply {
                                        append("Recepie:")
                                        addStyle(
                                            style = SpanStyle(textDecoration = TextDecoration.Underline),
                                            start = 0,
                                            end = "Recepie:".length
                                        )
                                    }.toAnnotatedString(),
                                    color = Color.Black,
                                    fontFamily = pixelfyFontFamily,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)){
                                cocktail.instructions?.let {
                                    Text(
                                        text = it,
                                        textAlign = TextAlign.Justify,
                                        modifier = Modifier.padding(bottom = 15.dp),
                                        color = Color.Black,
                                        fontFamily = pixelfyFontFamily,
                                    )
                                }
                            }
                    }
                }
            }}
        }
    }
}

