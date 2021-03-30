package com.example.androiddevchallenge

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Game(gameViewModel: GameViewModel = viewModel()) {

    val gameState: GameState by gameViewModel._gameStateflow.collectAsState(GameState())

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        LazyVerticalGrid(
            modifier = Modifier // 1
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Black),
            cells = GridCells.Fixed(boardSize), // 2
        ) {
            items(count = boardSize * boardSize) { // 3
                val color = if (gameState.cells[it].isAlive) {
                    Color.Black
                } else {
                    Color.White
                }
                Box(
                    Modifier // 4
                        .aspectRatio(1f)
                        .border(width = 2.dp, color = Color.Red)
                        .background(color)
                        .clickable {
                            gameViewModel.toggle(it)
                        }
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth().background(Color.Cyan),
            onClick = { gameViewModel.start() }
        ) {
            Text(text = "Start")
        }
    }


}