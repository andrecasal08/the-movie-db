package com.acdevs.themoviedb.screens

import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.acdevs.themoviedb.Results
import com.acdevs.themoviedb.viewmodels.HomeViewModel
import io.ktor.http.Url

@Composable
fun HomeScreen(viewModel: HomeViewModel, modifier: Modifier = Modifier
    .fillMaxSize()) {

    val sampleData = viewModel.characterData.collectAsState()

    /*Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        sampleData.value?.results?.forEach { movie ->
            Text(text = movie.title ?: "Loading...")
        }

    }*/

    LazyColumn(
    ) {
        item {
            Text(text = "Popular Movies",
                modifier = Modifier.padding(16.dp, 30.dp, 0.dp, 0.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        }
        item {
            PopularMovies(sampleData = sampleData.value?.results ?: emptyList())
        }

        item {
            TopRatedMovies()
        }

    }
}

@Composable
fun PopularMovies(modifier: Modifier = Modifier, sampleData: List<Results>) {
    LazyRow {
        var index = 0
        items(sampleData.count()) {

            Card(
                modifier = Modifier.padding(16.dp, 15.dp, 0.dp, 0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                onClick = {

                },

                ) {
                Column {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/original/${sampleData[index].posterPath}",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(150.dp)
                            .height(200.dp),
                        contentScale = ContentScale.FillBounds,
                    )
                    Text(text = sampleData[index].title, maxLines = 2,
                        modifier = Modifier
                            .width(150.dp)
                            .padding(5.dp))
                    Row(
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
                    ) {
                        Icon(imageVector = Icons.Outlined.Star, contentDescription = null)
                        Text(text = "8.4/10 IMDB",
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal)
                    }
                }
            }
            index++
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

}

/*
* @TODO
* - request new movies when scrolling in the lazy column/row (need to be new pages)
* */