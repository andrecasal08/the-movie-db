package com.acdevs.themoviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.acdevs.themoviedb.Movies
import com.acdevs.themoviedb.Results
import com.acdevs.themoviedb.remote.HttpRepository
import com.acdevs.themoviedb.utils.PopularMoviesUserSource
import com.acdevs.themoviedb.utils.TopRatedMoviesUserSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _popularMoviesData = MutableStateFlow<Movies?>(null)
    val popularMoviesData = _popularMoviesData.asStateFlow()
    private val repository = HttpRepository()

    private val _topRatedMoviesData = MutableStateFlow<Movies?>(null)
    val topRatedMoviesData = _topRatedMoviesData.asStateFlow()

    val topRatedMovies: Flow<PagingData<Results>> = Pager(PagingConfig(pageSize = 6)) {
        TopRatedMoviesUserSource()
    }.flow.cachedIn(viewModelScope)

    val popularMovies: Flow<PagingData<Results>> = Pager(PagingConfig(pageSize = 6)) {
        PopularMoviesUserSource()
    }.flow.cachedIn(viewModelScope)

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