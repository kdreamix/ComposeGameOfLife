package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.Game
import kotlinx.coroutines.launch

@Composable
fun GameScaffold() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { Drawer() },
        topBar = { TopAppBar(scaffoldState) },
        content = {
            Game()
        }
    )
}

@Composable
fun TopAppBar(scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Settings, "", tint = Color.White)
                }
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Game of Life")
                }
            }
        })
}