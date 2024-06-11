package com.vasco.tragui.widgets

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.ImageProvider
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.google.gson.Gson
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.dataManagment.FirebaseGetter.getRandomCocktail
import com.vasco.tragui.ui.screens.getGlassPainterByGlassType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val cocktailKey = stringPreferencesKey("cocktailKey")
private val gson = Gson()

class DrinkWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DrinkWidget()
}

class DrinkWidget : GlanceAppWidget() {
    override val sizeMode: SizeMode = SizeMode.Exact

    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        var currentCocktail: Cocktail = getRandomCocktail()

        provideContent {
            val preferences = currentState<Preferences>()
            val cocktailJson = preferences[cocktailKey]
            currentCocktail = if (cocktailJson != null) {
                gson.fromJson(cocktailJson, Cocktail::class.java)
            } else {
                currentCocktail
            }


            GlanceTheme {
                Box(
                    modifier = GlanceModifier
                        .fillMaxHeight()
                        .padding(horizontal = 10.dp)
                        .background(R.color.white),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
                    ) {

                        val imageResId = getGlassImageResId(currentCocktail.glassType)
                        Image(
                            provider = ImageProvider(imageResId),
                            contentDescription = "Glass Type",
                            modifier = GlanceModifier.size(80.dp)
                        )

                        Column(
                            modifier = GlanceModifier
                                .padding(5.dp)
                        ) {
                            Text(
                                modifier = GlanceModifier.clickable(
                                    onClick = actionRunCallback<RefreshAction>(),
                                ).padding(top = 2.dp, bottom = 5.dp, start = 10.dp),
                                text = currentCocktail.name,
                                style = TextStyle(
                                    color = ColorProvider(R.color.black),
                                    fontSize = 25.sp,
                                )
                            )
                            Text(
                                modifier = GlanceModifier.padding(start = 13.dp),
                                text = currentCocktail.category,
                                style = TextStyle(
                                    color = ColorProvider(R.color.black),
                                    fontSize = 16.sp,
                                )
                            )
                            Text(
                                modifier = GlanceModifier.padding(bottom = 10.dp, start = 13.dp),
                                text = currentCocktail.type,
                                style = TextStyle(
                                    color = ColorProvider(R.color.black),
                                    fontSize = 16.sp,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getGlassImageResId(glassType: String): Int {
    return when (glassType) {
        "Balloon Glass" -> R.drawable.balloon_glass
        "Beer Glass", "Pint glass" -> R.drawable.beer_glass
        "Brandy snifter" -> R.drawable.brandy_glass
        "Champagne Flute" -> R.drawable.champagne_glass
        "Cocktail Glass", "Cocktail glass" -> R.drawable.cocktail_glass
        "Coffee Mug", "Coffee mug" -> R.drawable.coffee_mug_glass
        "Collins Glass", "Collins glass" -> R.drawable.collins_glass
        "Copper Mug" -> R.drawable.copper_mug_glass
        "Half Bottle" -> R.drawable.esto_no_es_coca_papi
        "Highball Glass", "Highball glass" -> R.drawable.highball_glass
        "Hurricane glass" -> R.drawable.hurricane_glass
        "Margarita glass" -> R.drawable.margarita_glass
        "Mason jar" -> R.drawable.mason_jar_glass
        "Old-Fashioned glass", "Old-fashioned glass", "Whiskey sour glass" -> R.drawable.whiskeysour_glass
        "Pitcher" -> R.drawable.pitcher
        "Punch Bowl", "Punch bowl" -> R.drawable.punch_bowl_glass
        "Shot glass" -> R.drawable.shot_glass
        "Wine Glass", "Wine glass" -> R.drawable.wine_glass
        else -> R.drawable.starting_wine
    }
}


fun main() {
    // Ejemplo de cómo llamar a la función en el ámbito de una aplicación Android.
    // Asegúrate de llamar a este código en el lugar adecuado, como en un ViewModel o un Fragment.
    CoroutineScope(Dispatchers.Main).launch {
        val cocktail: Cocktail? = getRandomCocktail()
        if (cocktail != null) {
            // Usa el cóctel obtenido
            println("Nombre del cóctel: ${cocktail.name}")
        } else {
            println("No se encontró ningún cóctel.")
        }
    }
}

class SoundReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val mediaPlayer = MediaPlayer.create(context,R.raw.moscowmule)
        mediaPlayer.start()


    }
}

class RefreshAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val newCocktail = getRandomCocktail()
            updateAppWidgetState(context, glanceId) { prefs ->
                prefs[cocktailKey] = gson.toJson(newCocktail)
            }
            // Actualizar el widget
            DrinkWidget().update(context, glanceId)
        }
    }
}