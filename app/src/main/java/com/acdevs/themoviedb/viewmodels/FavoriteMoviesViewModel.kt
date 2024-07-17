package com.acdevs.themoviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acdevs.themoviedb.local.MoviesDao
import com.acdevs.themoviedb.local.MoviesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val moviesDao: MoviesDao
): ViewModel() {

    fun getFavoriteMovies(): Flow<List<MoviesEntity>> {
        return moviesDao.getAllMovies()
    }


}