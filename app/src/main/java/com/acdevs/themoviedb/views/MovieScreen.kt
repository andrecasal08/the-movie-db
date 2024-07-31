package com.acdevs.themoviedb.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.acdevs.themoviedb.data.Results
import com.acdevs.themoviedb.data.movieGenreList
import com.acdevs.themoviedb.utilities.AppFont
import com.acdevs.themoviedb.utilities.NavigationTopBar
import com.acdevs.themoviedb.utilities.cardBackgroundColor
import com.acdevs.themoviedb.utilities.cardTextColor
import com.acdevs.themoviedb.utilities.textTitleColor
import com.acdevs.themoviedb.viewmodels.MovieDetailsViewModel
import kotlinx.serialization.json.Json

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

    Scaffold(
        topBar = {
            NavigationTopBar(
                title = "The Movie DB",
                canNavigateBack = true,
                navigateUp = {
                    if (goBackCounter==0) {
                        navController.popBackStack()
                        goBackCounter++
                    }
                }
            )
        },
        bottomBar = {},
        containerColor = Color.White
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            item {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 30.dp, 0.dp, 0.dp)) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.8f),
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
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = movie.title,
                        modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp).weight(1f), // Allow text to take available space,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = AppFont.poppinsFont,
                        overflow = TextOverflow.Ellipsis, // Add ellipsis if text overflows
                        maxLines = 2)

                    var movieExists = viewModel.verifyMovieExistence(movie.title).collectAsState(
                        initial = false
                    )

                    var context = LocalContext.current
                    if (movieExists.value != null) {
                        IconButton(onClick = {
                            viewModel.deleteMovie(movieJson)
                            Toast.makeText(context, "Movie removed from favorites", Toast.LENGTH_SHORT).show()
                        },
                            modifier = Modifier.align(Alignment.CenterVertically).padding(16.dp, 16.dp, 0.dp, 0.dp).weight(0.2f)) {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = null,
                                tint = textTitleColor)
                        }
                    }
                    else {
                        IconButton(onClick = {
                            viewModel.insertMovie(movieJson)
                            Toast.makeText(context, "Movie added to favorites", Toast.LENGTH_SHORT).show()
                        },
                            modifier = Modifier.align(Alignment.CenterVertically).padding(16.dp, 16.dp, 0.dp, 0.dp).weight(0.2f)) {
                            Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null,
                                tint = textTitleColor)
                        }
                    }

                    

                }
            }

            item {
                Row(
                    modifier = Modifier.padding(20.dp, 5.dp, 0.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Star, contentDescription = null,
                        modifier = Modifier.size(15.dp),
                        tint = Color(255, 250, 94, 255)
                    )
                    val voteAverage = "%.1f".format(movie.voteAverage)
                    Text(text = "${voteAverage}/10 IMDB",
                        modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = AppFont.poppinsFont,
                        color = Color.Black.copy(alpha = 0.7f))
                }
            }

            item {
                LazyRow(
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)
                ) {
                    movieGenreList.forEach { (genre, value) ->
                        if (movie.genreIds.contains(value)) {
                            item {
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
                }
            }

            item {
                Text(text = "Description",
                    modifier = Modifier.padding(16.dp, 30.dp, 0.dp, 0.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = AppFont.poppinsFont,
                    color = textTitleColor)
            }

            item {
                Text(text = movie.overview,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = AppFont.poppinsFont,
                    color = Color.Black.copy(alpha = 0.7f))
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
                        Text(text = "Language",
                            fontWeight = FontWeight.Normal,
                            fontFamily = AppFont.poppinsFont,
                            color = Color.Black.copy(alpha = 0.7f))

                        Text(text = movie.originalLanguage.uppercase(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AppFont.poppinsFont)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Release Date",
                            fontWeight = FontWeight.Normal,
                            fontFamily = AppFont.poppinsFont)
                        Text(text = movie.releaseDate, fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AppFont.poppinsFont)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

}