package com.acdevs.themoviedb.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    var page: Int,
    var results: List<Results>,
)

@Serializable
data class Results(
    var id: Int,
    @SerialName("original_language")
    var originalLanguage: String,
    var title: String,
    var overview: String,
    var popularity: Double,
    @SerialName("poster_path")
    var posterPath: String,
    @SerialName("release_date")
    var releaseDate: String,
    @SerialName("vote_average")
    var voteAverage: Double,
    @SerialName("genre_ids")
    var genreIds: List<Int>
)

val movieGenreList = listOf(
    "Action" to 28,
    "Adventure" to 12,
    "Animation" to 16,
    "Comedy" to 35,
    "Crime" to 80,
    "Documentary" to 99,
    "Drama" to 18,
    "Family" to 10751,
    "Fantasy" to 14,
    "History" to 36,
    "Horror" to 27,
    "Music" to 10402,
    "Mystery" to 9648,
    "Romance" to 10749,
    "Science Fiction" to 878,
    "TV Movie" to 10770,
    "Thriller" to 53,
    "War" to 10752,
    "Western" to 37
)

@Serializable
data class Credits(
    var id: Int,
    var credits: List<Cast>
)

@Serializable
data class Cast(
    var id: Int,
    var name: String,
)