package com.acdevs.themoviedb.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.acdevs.themoviedb.data.Results
import com.acdevs.themoviedb.utilities.AppFont
import com.acdevs.themoviedb.utilities.BottomNavigationBar
import com.acdevs.themoviedb.utilities.NavigationTopBar
import com.acdevs.themoviedb.viewmodels.FavoriteMoviesViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier,
                    navController: NavController,
                    viewModel: FavoriteMoviesViewModel) {
    Scaffold(
        topBar = {
            NavigationTopBar(
                title = "Favorite Movies",
                canNavigateBack = false
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .padding(0.dp, it.calculateTopPadding(), 0.dp, it.calculateBottomPadding() + 10.dp)
                .fillMaxSize()
        ) {
            val dataList = viewModel.getFavoriteMovies().collectAsState(initial = emptyList())

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                println(dataList.value)
                if (dataList.value.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "No movies found.",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = AppFont.poppinsFont,
                                color = Color.Black.copy(alpha = 0.7f))
                        }
                    }
                }
                items(dataList.value) { item ->

                    var genres = emptyList<Int>()
                    genres = item.genreIds.split(",").map { it.toInt() }

                    var result = Results(
                        originalLanguage = item.originalLanguage,
                        overview = item.overview,
                        posterPath = item.posterPath,
                        releaseDate = item.releaseDate,
                        title = item.title,
                        voteAverage = item.voteAverage,
                        genreIds = genres,
                        popularity = 0.0,
                        id = 0
                    )
                    FavoriteMoviesCard(movie = result, navController = navController)
                }



            }
        }
    }
}

@Composable
fun FavoriteMoviesCard(movie: Results, navController: NavController) {
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
                Text(text = movie.title.toString(),
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