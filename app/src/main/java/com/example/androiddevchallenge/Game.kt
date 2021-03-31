package com.example.androiddevchallenge

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.data.toad
import com.example.androiddevchallenge.ui.theme.typography


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Game(gameViewModel: GameViewModel = viewModel()) {

    val gameState: GameState by gameViewModel.gameStateflow.collectAsState(GameState())

    Log.v("Game","Cells count : ${gameState.cells.size}")
    Log.v("Game","Board size : ${gameState.boardSize}")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        val numberOfCells: Int = gameState.cells.size
        Text(text = "Game of Life", style = typography.h3, color = Color.White)
        LazyVerticalGrid(
            modifier = Modifier // 1
                .fillMaxWidth()
                .border(1.dp, Color.White)
                .padding(16.dp)
                .wrapContentHeight()
                .background(Color.White),
            cells = GridCells.Fixed(gameState.boardSize), // 2
        ) {
            items(count = numberOfCells) { // 3
                val color = if (gameState.cells[it].isAlive) {
                    Color.White
                } else {
                    Color.Black
                }
                Box(
                    Modifier // 4
                        .aspectRatio(1f)
                        .padding(1.dp)
                        .background(color)
                        .clickable {
                            gameViewModel.toggle(it)
                        }
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan),
            onClick = { gameViewModel.start() }
        ) {
            Text(text = "Start")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan),
            onClick = { gameViewModel.pauseResume() }
        ) {
            Text(text = "Pause")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan),
            onClick = { gameViewModel.reset() }
        ) {
            Text(text = "Reset")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan),
            onClick = { gameViewModel.setPattern(toad) }
        ) {
            Text(text = "Toad")
        }
//        var frameRate: Long by remember { mutableStateOf(gameViewModel.gameStateflow.value.frameRate) }
//        TextField(
//            value = frameRate.toString(),
//            onValueChange = {
//                val newFrameRate = it.toLongOrNull()?:0
//                frameRate = newFrameRate
//                gameViewModel.onFrameRateChange(newFrameRate)
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//        )
    }


}