package com.acdevs.themoviedb.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.acdevs.themoviedb.Results
import com.acdevs.themoviedb.movieGenreList
import com.acdevs.themoviedb.viewmodels.HomeViewModel
import com.acdevs.themoviedb.viewmodels.MovieDetailsViewModel
import kotlinx.serialization.json.Json
import kotlin.math.round

@Composable
fun MovieScreen(modifier: Modifier = Modifier
    .fillMaxSize(), movieJson: String, viewModel: MovieDetailsViewModel,
                navController: NavController
) {
    val movie = Json.decodeFromString<Results>(movieJson)

    /*val movieCast = viewModel.movieCast.collectAsState()
    LaunchedEffect(key1 = movie.id) { // Trigger the effect when movieId changes
        println("Requesting... movie id: ${movie.id}")
        viewModel.getMovieCast(movie.id)
    }*/

    var goBackCounter by remember {
        mutableIntStateOf(0)
    }
    println(goBackCounter)

    LazyColumn() {
        item {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 30.dp, 0.dp, 0.dp)) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f),
                    contentScale = ContentScale.FillBounds
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            if (goBackCounter==0) {
                                navController.popBackStack()
                                goBackCounter++
                            }
                        },
                       // modifier = Modifier.align(Alignment.TopStart) // Position at top-left
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Back"
                        )
                    }


                    IconButton(
                        onClick = {

                        },
                       // modifier = Modifier.align(Alignment.TopStart) // Position at top-left
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            tint = Color.White,
                            contentDescription = "Back"
                        )
                    }
                }


            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.title,
                    modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)
                /*IconButton(onClick = { *//*TODO*//* }) {
                    Icon(imageVector = Icons.Outlined.Favorite, contentDescription = null)
                }*/
            }
        }

        item {
            Row(
                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Star, contentDescription = null,
                    modifier = Modifier.size(15.dp))
                val voteAverage = "%.1f".format(movie.voteAverage)
                Text(text = "${voteAverage}/10 IMDB",
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal)
            }
        }

        item {
            LazyRow(
                modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp)
            ) {
                movieGenreList.forEach { (genre, value) ->
                    if (movie.genreIds.contains(value)) {
                        item {
                            Card(
                                modifier = Modifier.padding(0.dp, 5.dp, 5.dp, 5.dp),
                                colors = CardDefaults.cardColors(

                                ),
                            ) {
                                Text(text = genre, fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(10.dp, 1.dp, 10.dp, 1.dp))
                            }
                        }
                    }
                }
            }
        }

        item {
            Text(text = "Description",
                modifier = Modifier.padding(16.dp, 30.dp, 0.dp, 0.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        }

        item {
            Text(text = movie.overview,
                modifier = Modifier.padding(16.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal)
        }

        item {
            Row(
                modifier = Modifier
                    .padding(16.dp, 16.dp, 0.dp, 0.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Language")
                    Text(text = movie.originalLanguage.uppercase(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Release Date")
                    Text(text = movie.releaseDate, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}