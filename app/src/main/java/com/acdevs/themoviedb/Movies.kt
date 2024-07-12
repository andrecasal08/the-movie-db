package com.acdevs.themoviedb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    var page: Int,
    var results: List<Results>,
)

@Serializable
data class Results(
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
    var voteAverage: Double
)