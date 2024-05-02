package com.vasco.tragui.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vasco.tragui.R
import androidx.lifecycle.lifecycleScope
import com.vasco.tragui.store.DiskDataStore
import kotlinx.coroutines.launch

class DrinkCabinetScreen: Screen {


    @Composable
    override fun Content() {

        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val dataStore = DiskDataStore(context)
        val name = dataStore.getSelectedBottles().collectAsState(initial = "")

        val navigator = LocalNavigator.currentOrThrow

        var input by remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.white))
        ) {
            Text(text = "Cabinet", fontSize = 20.sp)
            OutlinedTextField(value = input, onValueChange = { input = it })
            Button(onClick = {
                scope.launch {
                    dataStore.saveBottles(input)
                }
            }) {
                Text(text = "Save")
            }
            Text(
                text = name.value!!,
                color = Color.Black,
                fontSize = 18.sp
            )
        }

    }

    fun test() {
    }
}