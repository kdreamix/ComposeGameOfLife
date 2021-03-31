package com.example.androiddevchallenge.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Pattern

@Composable
fun HorizontalPatternsList(patterns: List<Pattern>, onPatternClick: (Pattern) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(patterns) { pattern ->
            PatternItem(pattern, onPatternClick = { onPatternClick(it) })
        }
    }
}

@Composable
fun PatternItem(pattern: Pattern, onPatternClick: (Pattern) -> Unit) {
    Card(Modifier.clickable { onPatternClick(pattern) }) {
        Column {
            Image(painter = painterResource(id = R.drawable.ic_launcher_background), "")
            Text(text = pattern.title)
        }
    }
}