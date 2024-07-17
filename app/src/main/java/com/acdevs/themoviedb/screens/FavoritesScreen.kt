package com.acdevs.themoviedb.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.acdevs.themoviedb.utils.BottomNavigationBar
import com.acdevs.themoviedb.viewmodels.FavoriteMoviesViewModel

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier,
                    navController: NavController,
                    viewModel: FavoriteMoviesViewModel) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val dataList = viewModel.getFavoriteMovies().collectAsState(initial = emptyList())

            LazyColumn {
                items(dataList.value) { item ->
                    Surface {
                        Text(text = item.title.toString())
                    }
                }
            }
        }
    }
}