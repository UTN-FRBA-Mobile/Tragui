package com.vasco.tragui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.vasco.tragui.R
import com.vasco.tragui.dataManagment.Cocktail
import com.vasco.tragui.dataManagment.FirebaseGetter.getRandomCocktail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class WidgetPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE)

    var cocktailTitle: String?
        get() = sharedPreferences.getString("CocktailTitle", null)
        set(value) {
            sharedPreferences.edit().putString("CocktailTitle", value).apply()
        }
}


class DrinkWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DrinkWidget()
}

class DrinkWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Exact

    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // Obtener el cóctel aleatorio

        val preferences: WidgetPreferences by lazy { WidgetPreferences(context) }

        var cocktail: Cocktail = getRandomCocktail()

        // Obtener el título guardado en preferencias
        val title = preferences.cocktailTitle ?: cocktail.name

        // Proveer contenido del widget
        provideContent {
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
                        Column(
                            modifier = GlanceModifier
                                .padding(5.dp)
                        ) {
                            Text(
                                modifier = GlanceModifier.clickable(
                                    onClick = actionRunCallback<RefreshAction>(),
                                ).padding(top = 2.dp, bottom = 5.dp, start = 10.dp),
                                text = title,
                                style = TextStyle(
                                    color = ColorProvider(R.color.black),
                                    fontSize = 25.sp,
                                )
                            )
                            Text(
                                modifier = GlanceModifier.padding(start = 13.dp),
                                text = cocktail.category,
                                style = TextStyle(
                                    color = ColorProvider(R.color.black),
                                    fontSize = 16.sp,
                                )
                            )
                            Text(
                                modifier = GlanceModifier.padding(bottom = 10.dp, start = 13.dp),
                                text = cocktail.type,
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

class RefreshAction : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val preferences = WidgetPreferences(context)
        preferences.cocktailTitle = getRandomCocktail().name

        // Actualizar el widget
        DrinkWidget().update(context, glanceId)
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
