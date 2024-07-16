package com.acdevs.themoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.acdevs.themoviedb.screens.FavoritesScreen
import com.acdevs.themoviedb.screens.HomeScreen
import com.acdevs.themoviedb.screens.MovieScreen
import com.acdevs.themoviedb.ui.theme.TheMovieDBTheme
import com.acdevs.themoviedb.viewmodels.HomeViewModel
import com.acdevs.themoviedb.viewmodels.MovieDetailsViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheMovieDBTheme {

                val viewModel by viewModels<HomeViewModel>()
                val detailsViewModel by viewModels<MovieDetailsViewModel>()
                //HomeScreen(viewModel = viewModel)
                //val scope = rememberCoroutineScope()

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
                        FavoritesScreen(navController = navController)
                    }
                }

            }
        }
    }
}