package com.acdevs.themoviedb.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.acdevs.themoviedb.data.Results
import com.acdevs.themoviedb.data.movieGenreList
import com.acdevs.themoviedb.utilities.AppFont
import com.acdevs.themoviedb.utilities.BottomNavigationBar
import com.acdevs.themoviedb.utilities.NavigationTopBar
import com.acdevs.themoviedb.utilities.cardBackgroundColor
import com.acdevs.themoviedb.utilities.cardTextColor
import com.acdevs.themoviedb.utilities.textTitleColor
import com.acdevs.themoviedb.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun HomeScreen(modifier: Modifier = Modifier.fillMaxSize(),
               viewModel: HomeViewModel, navController: NavController) {

    val movieListItems: LazyPagingItems<Results> = viewModel.topRatedMovies.collectAsLazyPagingItems()


    Scaffold(
        topBar = {
            NavigationTopBar(
                title = "The Movie DB",
                canNavigateBack = false
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        containerColor = Color.White
    ) {
        innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Row (
                    modifier = Modifier
                        .padding(16.dp, 30.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "The Movie TB", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }

            item {
                Text(text = "Popular Movies",
                    color = textTitleColor,
                    modifier = Modifier.padding(16.dp, 30.dp, 0.dp, 0.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = AppFont.poppinsFont)
                Spacer(modifier = Modifier.height(20.dp))

                PopularMovies(movies = viewModel.popularMovies, navController = navController)
            }

            item {
                Text(text = "Top Rated Movies", color = textTitleColor,
                    modifier = Modifier.padding(16.dp, 30.dp, 0.dp, 0.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = AppFont.poppinsFont)

                Spacer(modifier = Modifier.height(20.dp))
            }

            // Displaying top rated movies
            items(
                count = movieListItems.itemCount,
                key = movieListItems.itemKey{movie -> movie.title},
                contentType = movieListItems.itemContentType { movie -> "movie" }
            ) {
                    index ->
                val movie = movieListItems[index]
                if (movie != null) {
                    TopRatedMoviesCard(movie = movie, navController = navController)
                }
            }
        }
    }
}

@Composable
fun PopularMovies(modifier: Modifier = Modifier, movies: Flow<PagingData<Results>>, navController: NavController) {
    val movieListItems: LazyPagingItems<Results> = movies.collectAsLazyPagingItems()
    LazyRow(
        modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
    ) {
        items(
            count = movieListItems.itemCount,
            key = movieListItems.itemKey{movie -> movie.title},
            contentType = movieListItems.itemContentType { movie -> "movie" }
        ) {
                index ->
            val movie = movieListItems[index]
            if (movie != null) {
                PopularMoviesCard(movie = movie, navController = navController)
            }
        }
    }
}

@Composable
fun TopRatedMovies(modifier: Modifier = Modifier, movies: Flow<PagingData<Results>>, navController: NavController) {
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
              TopRatedMoviesCard(movie = movie, navController = navController)
          }
      }
  }
}

@Composable
fun PopularMoviesCard(modifier: Modifier = Modifier, movie: Results, navController: NavController) {
    Card(
        modifier = Modifier.padding(0.dp, 15.dp, 16.dp, 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = {
            val movieJson = Json.encodeToString(movie)
            println("moviejson: $movieJson")

            if (hasOneAmpersand(movie.title)) {
                println("The title has exactly one ampersand.")
            } else {
                println("The title does not have exactly one ampersand.")
            }

            val escapedJsonString = movieJson.replace("&", "and")

            navController.navigate("details_screen?movie=${escapedJsonString}")
        },

        ) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(150.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds,
            )
            Text(text = movie.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = AppFont.poppinsFont,
                maxLines = 3,
                modifier = Modifier
                    .width(150.dp)
                    .padding(5.dp))
            Row(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Star, contentDescription = null,
                    modifier = Modifier.size(15.dp),
                    tint = Color(255, 250, 94, 255))
                val voteAverage = "%.1f".format(movie.voteAverage)
                Text(text = "${voteAverage}/10 IMDB",
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = AppFont.poppinsFont,
                    color = Color.Black.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
fun TopRatedMoviesCard(movie: Results, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp, 15.dp, 16.dp, 0.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = {
            val movieJson = Json.encodeToString(movie)
            navController.navigate("details_screen?movie=${movieJson}")
        },

        ) {
        Row(
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds,
            )

            Column(
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Text(text = movie.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = AppFont.poppinsFont,
                    maxLines = 3,
                    modifier = Modifier
                        .width(150.dp)
                        .padding(5.dp))
                Row(
                    modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Star, contentDescription = null,
                        modifier = Modifier.size(15.dp),
                        tint = Color(255, 250, 94, 255))
                    val voteAverage = "%.1f".format(movie.voteAverage)
                    Text(text = "${voteAverage}/10 IMDB",
                        modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = AppFont.poppinsFont,
                        color = Color.Black.copy(alpha = 0.7f))
                }
                LazyRow(
                    modifier = Modifier.padding(5.dp)
                ) {
                    item {
                        GenreListCard(genres = movie.genreIds)
                    }
                }
            }
        }
    }
}

@Composable
fun GenreListCard(modifier: Modifier = Modifier, genres: List<Int>) {
    movieGenreList.forEach { (genre, value) ->
        if (genres.contains(value)) {
            Card(
                modifier = Modifier.padding(0.dp, 5.dp, 5.dp, 5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardBackgroundColor
                ),
            ) {
                Text(text = genre, fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = AppFont.poppinsFont,
                    modifier = Modifier.padding(10.dp, 1.dp, 10.dp, 1.dp),
                    color = cardTextColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

}

fun hasOneAmpersand(title: String): Boolean {
    return title.count { it == '&' } == 1
}

/*
* @TODO
* - request new movies when scrolling in the lazy column/row (need to be new pages) / pagination
* */