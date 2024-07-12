package com.acdevs.themoviedb.screens

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn {
        item {
            Text(text = "Popular Movies")
            PopularMovies()
        }
        item {
            Text(text = "Top Rated Movies")
            TopRatedMovies()
        }
    }
}

@Composable
fun PopularMovies(modifier: Modifier = Modifier) {
    LazyRow {
        items(10) { item ->
            MovieCard(inputText = item.toString())
        }
    }
}

@Composable
fun TopRatedMovies(modifier: Modifier = Modifier) {
    
}

@Composable
fun MovieCard(modifier: Modifier = Modifier, inputText : String) {
    Card(
        modifier = Modifier.wrapContentSize(),
    ) {
        Text(text = inputText)
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}