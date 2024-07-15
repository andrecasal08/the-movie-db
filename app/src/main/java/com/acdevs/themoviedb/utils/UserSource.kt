package com.acdevs.themoviedb.utils

import android.service.autofill.UserData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.acdevs.themoviedb.Results
import com.acdevs.themoviedb.remote.HttpRepository
import okio.IOException

class UserSource: PagingSource<Int, Results>() {
    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        val repository = HttpRepository()
        return try {
            val nextPage = params.key ?: 1
            val userList = repository.getTopRatedMovies(nextPage)

            LoadResult.Page(
                data = userList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = userList.page.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}