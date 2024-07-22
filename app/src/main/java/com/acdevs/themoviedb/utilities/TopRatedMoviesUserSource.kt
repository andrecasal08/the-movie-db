package com.acdevs.themoviedb.utilities

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.acdevs.themoviedb.data.Results
import com.acdevs.themoviedb.network.HttpRepository
import okio.IOException

class TopRatedMoviesUserSource: PagingSource<Int, Results>() {
    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        val repository = HttpRepository()
        return try {
            val nextPage = params.key ?: 1
            val topRatedMoviesList = repository.getTopRatedMovies(nextPage)

            LoadResult.Page(
                data = topRatedMoviesList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = topRatedMoviesList.page.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}