package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Drawer() {
    Column {
        Text(text = "Game of life")
        Text(text = "What is it?")
        Text(text = "Source code")
    }
}