package com.acdevs.themoviedb.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.acdevs.themoviedb.Results
import com.acdevs.themoviedb.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.Flow


@Composable
fun HomeScreen(viewModel: HomeViewModel,
               modifier: Modifier = Modifier.fillMaxSize()) {

    val popularMoviesData = viewModel.popularMoviesData.collectAsState()
    val topRatedMoviesData = viewModel.topRatedMoviesData.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Popular Movies",
            modifier = Modifier.padding(16.dp, 30.dp, 0.dp, 0.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold)

        PopularMovies(popularMoviesData = popularMoviesData.value?.results ?: emptyList())

        Text(text = "Top Rated Movies", modifier = Modifier.padding(16.dp, 30.dp, 0.dp, 0.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold)


        TopRatedMovies(topRatedMoviesList = topRatedMoviesData.value?.results ?: emptyList(), movies = viewModel.movie)
    }

}

@Composable
fun PopularMovies(modifier: Modifier = Modifier, popularMoviesData: List<Results>) {
    LazyRow {
        popularMoviesData.forEach { movie ->
            item {
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
                            model = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(150.dp)
                                .height(200.dp),
                            contentScale = ContentScale.FillBounds,
                        )
                        Text(text = movie.title, maxLines = 2,
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
            }
        }
    }
}

@Composable
fun TopRatedMovies(modifier: Modifier = Modifier, topRatedMoviesList: List<Results>, movies: Flow<PagingData<Results>>) {
    val movieListItems: LazyPagingItems<Results> = movies.collectAsLazyPagingItems()

  LazyColumn {
      items(
          count = movieListItems.itemCount,
          key = movieListItems.itemKey{movie -> movie.title},
          contentType = movieListItems.itemContentType { movie -> "movie" }
      ) {
          index ->
          val movie = movieListItems[index]
          if (movie != null) {
              MovieData(movie = movie)
          }
      }
  }

    /*LazyColumn {
        topRatedMoviesList.forEach { movie ->
            item {
                Card(
                    modifier = Modifier
                        .padding(16.dp, 15.dp, 16.dp, 0.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    onClick = {

                    },

                    ) {
                    Row {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                            contentDescription = null,
                            modifier = Modifier
                                .width(150.dp)
                                .height(200.dp),
                            contentScale = ContentScale.FillBounds,
                        )

                        Column {
                            Text(text = movie.title, maxLines = 2,
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
                }
            }
        }
    }*/
}

@Composable
fun MovieData(movie: Results) {
    Card(
        modifier = Modifier
            .padding(16.dp, 15.dp, 16.dp, 0.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = {

        },

        ) {
        Row {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp),
                contentScale = ContentScale.FillBounds,
            )

            Column {
                Text(text = movie.title, maxLines = 2,
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
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

}

/*
* @TODO
* - request new movies when scrolling in the lazy column/row (need to be new pages) / pagination
* */