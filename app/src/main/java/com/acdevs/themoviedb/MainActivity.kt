package com.acdevs.themoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.acdevs.themoviedb.screens.HomeScreen
import com.acdevs.themoviedb.screens.HomeScreenPreview
import com.acdevs.themoviedb.screens.MovieScreen
import com.acdevs.themoviedb.ui.theme.TheMovieDBTheme
import com.acdevs.themoviedb.utils.NavigationTopBar
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
                }
            }
        }
    }
}