package com.acdevs.themoviedb.viewmodels

import androidx.lifecycle.ViewModel
import com.acdevs.themoviedb.local.MoviesDao
import com.acdevs.themoviedb.local.MoviesEntity
import kotlinx.coroutines.flow.Flow

class FavoriteMoviesViewModel(
    private val moviesDao: MoviesDao
): ViewModel() {

    fun getFavoriteMovies(): Flow<List<MoviesEntity>> {
        return moviesDao.getAllMovies()
    }
}