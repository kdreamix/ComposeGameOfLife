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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.typography


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Game(gameViewModel: GameViewModel = viewModel()) {

    val gameState: GameState by gameViewModel.gameStateflow.collectAsState(GameState())

    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.background(Color.Black)) {
        Text(text = "Game of Life", style = typography.h3)
        LazyVerticalGrid(
            modifier = Modifier // 1
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentHeight()
                .background(Color.Black),
            cells = GridCells.Fixed(boardSize), // 2
        ) {
            items(count = boardSize * boardSize) { // 3
                val color = if (gameState.cells[it].isAlive) {
                    Color.White
                } else {
                    Color.Black
                }
                Box(
                    Modifier // 4
                        .aspectRatio(1f)
                        .border(2.dp,Color.White)
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
    }


}