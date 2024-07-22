package com.acdevs.themoviedb.utilities

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.acdevs.themoviedb.data.Results
import com.acdevs.themoviedb.network.HttpRepository
import okio.IOException

class PopularMoviesUserSource: PagingSource<Int, Results>() {
    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        val repository = HttpRepository()
        return try {
            val nextPage = params.key ?: 1
            val popularMoviesList = repository.getPopularMovies(nextPage)

            LoadResult.Page(
                data = popularMoviesList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = popularMoviesList.page.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}