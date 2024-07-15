package com.acdevs.themoviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acdevs.themoviedb.Cast
import com.acdevs.themoviedb.Credits
import com.acdevs.themoviedb.remote.HttpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel: ViewModel() {
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
}