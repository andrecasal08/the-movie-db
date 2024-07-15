package com.acdevs.themoviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acdevs.themoviedb.Movies
import com.acdevs.themoviedb.remote.HttpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _popularMoviesData = MutableStateFlow<Movies?>(null)
    val popularMoviesData = _popularMoviesData.asStateFlow()
    private val repository = HttpRepository()

    private val _topRatedMoviesData = MutableStateFlow<Movies?>(null)
    val topRatedMoviesData = _topRatedMoviesData.asStateFlow()

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getPopularMovies()
            }.onSuccess {
                _popularMoviesData.value = it
            }.onFailure {
                _popularMoviesData.value = null
            }
        }

        viewModelScope.launch {
            kotlin.runCatching {
                repository.getTopRatedMovies()
            }.onSuccess {
                _topRatedMoviesData.value = it
            }.onFailure {
                _topRatedMoviesData.value = null
            }
        }
    }
}