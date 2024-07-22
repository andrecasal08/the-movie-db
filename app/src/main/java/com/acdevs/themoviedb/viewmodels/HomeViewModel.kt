package com.acdevs.themoviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.acdevs.themoviedb.Results
import com.acdevs.themoviedb.utils.PopularMoviesUserSource
import com.acdevs.themoviedb.utils.TopRatedMoviesUserSource
import kotlinx.coroutines.flow.Flow

class HomeViewModel: ViewModel() {
    val topRatedMovies: Flow<PagingData<Results>> = Pager(PagingConfig(pageSize = 6)) {
        TopRatedMoviesUserSource()
    }.flow.cachedIn(viewModelScope)

    val popularMovies: Flow<PagingData<Results>> = Pager(PagingConfig(pageSize = 6)) {
        PopularMoviesUserSource()
    }.flow.cachedIn(viewModelScope)
}