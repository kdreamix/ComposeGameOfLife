package com.example.androiddevchallenge

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Game(gameViewModel: GameViewModel = viewModel()) {

    val gameState: GameState by gameViewModel._gameStateflow.collectAsState(GameState())

    LazyVerticalGrid(
        modifier = Modifier // 1
            .fillMaxSize()
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
}