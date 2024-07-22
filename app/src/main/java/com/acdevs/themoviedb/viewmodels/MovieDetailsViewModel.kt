package com.acdevs.themoviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acdevs.themoviedb.data.Results
import com.acdevs.themoviedb.local.MoviesDao
import com.acdevs.themoviedb.local.MoviesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MovieDetailsViewModel(
    private val moviesDao: MoviesDao,
) : ViewModel() {
    fun insertMovie(movieJson: String) {
        val movie = Json.decodeFromString<Results>(movieJson)
        val movieEntity = MoviesEntity(
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage,
            originalLanguage = movie.originalLanguage,
            genreIds = movie.genreIds.joinToString(","),
        )
        viewModelScope.launch {
            moviesDao.insertMovie(movieEntity)
        }
    }

    fun deleteMovie(movieJson: String) {
        val movie = Json.decodeFromString<Results>(movieJson)
        viewModelScope.launch {
            moviesDao.deleteMovie(movie.title)
        }
    }

    fun verifyMovieExistence(movie: String): Flow<MoviesEntity> {
        return moviesDao.getMovieByTitle(movie)
    }
}