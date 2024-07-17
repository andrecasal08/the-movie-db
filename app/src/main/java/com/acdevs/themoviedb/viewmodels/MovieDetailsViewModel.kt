package com.acdevs.themoviedb.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acdevs.themoviedb.Credits
import com.acdevs.themoviedb.Results
import com.acdevs.themoviedb.local.MoviesDao
import com.acdevs.themoviedb.local.MoviesEntity
import com.acdevs.themoviedb.remote.HttpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MovieDetailsViewModel(
    private val moviesDao: MoviesDao,
) : ViewModel() {
    private val repository = HttpRepository()
    private val _movieCast = MutableStateFlow<Credits?>(null)
    val movieCast = _movieCast.asStateFlow()


    suspend fun getMovieCast(movieId: Int) {
        kotlin.runCatching {
            println("getting... movie id: $movieId")
            repository.getMovieCast(movieId)
        }.onSuccess {
            println("success")
            _movieCast.value = it
        }.onFailure { throwable ->
            println(throwable.message)
            println("error")
            _movieCast.value = null
        }
    }

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