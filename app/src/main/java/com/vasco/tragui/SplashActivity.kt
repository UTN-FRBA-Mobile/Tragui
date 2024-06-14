package com.vasco.tragui

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vasco.tragui.ui.animations.Animations
import com.vasco.tragui.ui.animations.Animations.GifImageLogo
import com.vasco.tragui.ui.theme.TraguiTheme
import kotlinx.coroutines.delay


@SuppressLint("CustomSplashScreen")
class SplashActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            TraguiTheme {
                SplashScreen()

            }

        }

    }

    @Composable
    private fun SplashScreen() {
        val context = LocalContext.current


        LaunchedEffect(key1 = true) {
            delay(300)
            val mediaPlayer = MediaPlayer.create(context, R.raw.surinam)
            mediaPlayer.start()
            delay(2000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
        Box( modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.splash_screen)), contentAlignment = Alignment.Center

        ){


            val mediaPlayer = MediaPlayer.create(context, R.raw.roll)

            GifImageLogo(Modifier.align(Alignment.Center))
            /*
            Image(modifier = Modifier
                .size(170.dp).padding(start = 4.dp), painter = painterResource(id = R.drawable.starting_wine),
                contentDescription = null)

             */
        }
    }
}