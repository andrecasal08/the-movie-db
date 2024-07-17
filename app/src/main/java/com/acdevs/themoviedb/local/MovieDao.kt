package com.acdevs.themoviedb.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert
    suspend fun insertMovie(moviesEntity: MoviesEntity)

    @Delete
    suspend fun deleteMovie(moviesEntity: MoviesEntity)

    @Query("SELECT * FROM movies_table")
    fun getAllMovies(): Flow<List<MoviesEntity>>
}