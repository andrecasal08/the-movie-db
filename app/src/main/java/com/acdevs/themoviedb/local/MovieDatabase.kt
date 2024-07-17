package com.acdevs.themoviedb.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MoviesEntity::class],
    version = 1
)
abstract class MoviesDatabase: RoomDatabase()  {
    abstract val moviesDao: MoviesDao
}