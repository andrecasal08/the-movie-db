package com.acdevs.themoviedb.network

import com.acdevs.themoviedb.data.Credits
import com.acdevs.themoviedb.Keys
import com.acdevs.themoviedb.data.Movies
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpRepository {

    suspend fun getPopularMovies(page: Int=1): Movies {

        val httpClient = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation){
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        return httpClient.get("https://api.themoviedb.org/3/movie/popular?api_key=${Keys().moviesApiKey}&language=en-US&page=${page}" +
                "").body()
    }

    suspend fun getTopRatedMovies(page: Int=1): Movies {
        val httpClient = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation){
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        println("getTopRatedMovies: page: $page")
        return httpClient.get("https://api.themoviedb.org/3/movie/top_rated?api_key=${Keys().moviesApiKey}&language=en-US&page=${page}").body()
    }

    suspend fun getMovieCast(movieId: Int): Credits {
        val httpClient = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation){
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        return httpClient.get("https://api.themoviedb.org/3/movie/${movieId}?api_key=${Keys().moviesApiKey}&append_to_response=credits").body()
    }
}