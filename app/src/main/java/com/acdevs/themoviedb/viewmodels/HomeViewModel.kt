package com.acdevs.themoviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acdevs.themoviedb.Movies
import com.acdevs.themoviedb.remote.HttpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _characterData = MutableStateFlow<Movies?>(null)
    val characterData = _characterData.asStateFlow()
    private val repository = HttpRepository()

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getPopularMovies()
            }.onSuccess {
                _characterData.value = it
            }.onFailure {
                _characterData.value = null
            }
        }
    }
}