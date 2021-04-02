package com.example.androiddevchallenge

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.data.Pattern
import com.example.androiddevchallenge.data.blinker
import com.example.androiddevchallenge.data.toad
import com.example.androiddevchallenge.ui.HorizontalPatternsList
import com.example.androiddevchallenge.ui.theme.typography


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Game(gameViewModel: GameViewModel = viewModel()) {

    val gameState: GameState by gameViewModel.gameStateflow.collectAsState(GameState())

    Log.v("Game", "Cells count : ${gameState.cells.size}")
    Log.v("Game", "Board size : ${gameState.boardSize}")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {

        GameGrid(gameState, onBoxClick = {
            gameViewModel.toggle(it)
        })

        BottomPanel(
            gameState = gameState,
            onFrameRateChanged = { gameViewModel.onFrameRateChange(it) },
            onBoardSizeChange = { gameViewModel.setBoardSize(it) },
            onGameReset = { gameViewModel.reset() },
            onGameToggle = { gameViewModel.pauseResume() },
            onGameStart = { gameViewModel.start() },
            onPatternClick = { gameViewModel.setPattern(it) }
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomPanel(
    gameState: GameState,
    onFrameRateChanged: (Long) -> Unit,
    onBoardSizeChange: (Int) -> Unit,
    onGameStart: () -> Unit,
    onGameToggle: () -> Unit,
    onGameReset: () -> Unit,
    onPatternClick: (Pattern) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        stickyHeader {
            Text(text = "Controls", style = typography.h5)
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { onGameStart() }
                ) {
                    Text(text = "Start")
                }
                OutlinedButton(
                    onClick = { onGameToggle() }
                ) {
                    Text(text = "Pause/Resume")
                }
                OutlinedButton(
                    onClick = { onGameReset() }
                ) {
                    Text(text = "Reset")
                }
            }

        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Interval")
                Slider(
                    value = gameState.frameRate.toFloat(),
                    onValueChange = { onFrameRateChanged(it.toLong()) },
                    valueRange = 200f..2000f
                )
            }
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Board size")
                Slider(
                    value = gameState.boardSize.toFloat(),
                    onValueChange = { onBoardSizeChange(it.toInt()) },
                    valueRange = 10f..30f
                )
            }
        }

        stickyHeader {
            Text(text = "Patterns", style = typography.h5)
        }

        item {
            HorizontalPatternsList(
                patterns = listOf(blinker, toad),
                onPatternClick = { onPatternClick(it) })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameGrid(gameState: GameState, onBoxClick: (Int) -> Unit) {
    val numberOfCells: Int = gameState.cells.size
    // set up all transformation states
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        offset += offsetChange
    }
    LazyVerticalGrid(
        modifier = Modifier
            // apply other transformations like rotation and zoom on the pizza slice emoji
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x,
                translationY = offset.y
            )
            // add transformable to listen to multitouch transformation events after offset
            .transformable(state = state)// 1
            .fillMaxWidth()
            .border(1.dp, Color.White)
            .padding(16.dp)
            .wrapContentHeight()
            .background(Color.White),
        cells = GridCells.Fixed(gameState.boardSize), // 2
    ) {
        items(count = numberOfCells) { index ->
            val color = if (gameState.cells[index].isAlive) {
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
                        onBoxClick(index)
                    }
            )
        }
    }
}