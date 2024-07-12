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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.acdevs.themoviedb.screens.HomeScreen
import com.acdevs.themoviedb.ui.theme.TheMovieDBTheme
import com.acdevs.themoviedb.viewmodels.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheMovieDBTheme {
                Surface(onClick = { /*TODO*/ }) {
                    val viewModel by viewModels<HomeViewModel>()
                    HomeScreen(viewModel = viewModel)
                    //val scope = rememberCoroutineScope()

                }
            }
        }
    }
}