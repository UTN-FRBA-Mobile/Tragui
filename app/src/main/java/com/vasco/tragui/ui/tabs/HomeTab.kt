package com.vasco.tragui.ui.tabs

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.vasco.tragui.R
import com.vasco.tragui.store.DiskDataStore
import com.vasco.tragui.ui.screens.CocktailDetailScreen
import com.vasco.tragui.ui.screens.CocktailListScreen
import com.vasco.tragui.ui.screens.HomeFirstTimeScreen
import java.util.logging.Logger

object HomeTab: Tab {
    val logger = Logger.getLogger("HomeTab")
    private fun readResolve(): Any = HomeTab
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.app_name)
            val icon = painterResource(id = R.drawable.cocktail_menu)
//            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }



    @Composable
    override fun Content() {

        logger.info("Home tab initialized")
        logger.info("${LocalContext.current}")

        fun getDetailIdFromIntent(intent: Intent?): String? {
            if (intent != null) {
                val data: Uri? = intent.data
                return data?.getQueryParameter("id")
            }
            return null
        }
        fun Context.findActivity(): Activity? = when (this) {
            is Activity -> this
            is ContextWrapper -> baseContext.findActivity()
            else -> null
        }
        val context = LocalContext.current
        val activity = context.findActivity()
        val cocktailId = getDetailIdFromIntent(activity?.intent)

        Navigator(
            //HomeFirstTimeScreen()
            screens = listOf(
                CocktailListScreen(),
                HomeFirstTimeScreen(),
                CocktailDetailScreen(cocktail_id = cocktailId)
            )
        )
    }
}