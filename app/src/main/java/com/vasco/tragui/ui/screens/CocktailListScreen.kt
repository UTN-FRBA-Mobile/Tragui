package com.vasco.tragui.ui.screens;

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.ui.components.PixelCocktailContainer
import com.vasco.tragui.ui.theme.pixelfyFontFamily



// definicion de los cocteles del inicio
val cocktails = listOf(
    Cocktail("Margarita", "12345", "", "Classic Cocktail", "", "WISKY", "", listOf("2 oz tequila", "1 oz orange liqueur", "1 oz fresh lemon juice", "Salt for the rim"), "Moisten the rim of a cocktail glass with lime and then with salt. Add all ingredients to a shaker with ice. Shake well and strain into the prepared glass. Enjoy!", listOf("2 oz", "1 oz", "1 oz", "To taste"), "", "https://example.com/margarita.jpg", "Alcoholic"),
    Cocktail("Mojito1", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito2", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito3", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito4", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito5", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito6", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito7", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito8", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito9", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito10", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    Cocktail("Mojito11", "54321", "", "Classic Cocktail", "", "RUM", "", listOf("2 oz white rum", "1/2 oz simple syrup", "1/2 oz fresh lime juice", "Club soda", "Mint leaves"), "Muddle mint leaves and simple syrup in a glass. Add rum, lime juice, and ice. Top with club soda. Stir gently and garnish with mint leaves. Enjoy!", listOf("2 oz", "1/2 oz", "1/2 oz", "To taste", "To taste"), "", "https://example.com/mojito.jpg", "Alcoholic"),
    )


//LOGICA DE LA PAGINA PRINCIPAL
class CocktailListScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow


         Column(
             modifier = Modifier
                 .background(colorResource(id = R.color.primary_light ))
         ){
                         // este es el titulo
                         Text(text = "Possible Drinks",
                             fontFamily = pixelfyFontFamily,
                             fontSize = 40.sp,
                             fontWeight = FontWeight.SemiBold,
                             color = colorResource(R.color.primary_dark),
                             modifier = Modifier
                                 .padding(bottom = 20.dp, top = 25.dp)
                                 .align(Alignment.CenterHorizontally)
                         )

                 LazyColumn ( modifier = Modifier.fillMaxSize())
                         {

                             // aca es donde se crean todas las tarjetas de los tragos de la pagina principal
                             items (cocktails) {cocktail ->
                                 Box(
                                     modifier = Modifier
                                         .clickable { navigator.push(CocktailDetailScreen(cocktail.id_api)) }
                                 ) {
                                     PixelCocktailContainer(cocktail)
                                 }
                             }
                         }


         }


    }
}
