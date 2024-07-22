package com.acdevs.themoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.acdevs.themoviedb.local.MoviesDatabase
import com.acdevs.themoviedb.views.FavoritesScreen
import com.acdevs.themoviedb.views.HomeScreen
import com.acdevs.themoviedb.views.MovieScreen
import com.acdevs.themoviedb.ui.theme.TheMovieDBTheme
import com.acdevs.themoviedb.viewmodels.FavoriteMoviesViewModel
import com.acdevs.themoviedb.viewmodels.HomeViewModel
import com.acdevs.themoviedb.viewmodels.MainViewModel
import com.acdevs.themoviedb.viewmodels.MovieDetailsViewModel

class MainActivity : ComponentActivity() {

    private val database by lazy {

        Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java, "favorite_movies.db"
        ).build()
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }

        enableEdgeToEdge()
        setContent {
            TheMovieDBTheme {

                val viewModel by viewModels<HomeViewModel>()
                //val detailsViewModel by viewModels<MovieDetailsViewModel>()

                val detailsViewModel by viewModels<MovieDetailsViewModel> (
                    factoryProducer = {
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return MovieDetailsViewModel(database.moviesDao) as T
                            }
                        }
                    }
                )

                val favoriteMoviesViewModel by viewModels<FavoriteMoviesViewModel> (
                    factoryProducer = {
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return FavoriteMoviesViewModel(database.moviesDao) as T
                            }
                        }
                    }
                )


                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home_screen") {
                    composable("home_screen"){
                        HomeScreen(viewModel = viewModel, navController = navController)
                    }
                    composable(
                        route = "details_screen?movie={movieJson}",
                        arguments = listOf(navArgument("movieJson") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val movieJson = backStackEntry.arguments?.getString("movieJson") ?: ""
                        MovieScreen(movieJson = movieJson, viewModel=detailsViewModel, navController = navController)
                    }
                    composable("favorites_screen") {
                        FavoritesScreen(navController = navController, viewModel=favoriteMoviesViewModel)
                    }
                }

            }
        }
    }
}