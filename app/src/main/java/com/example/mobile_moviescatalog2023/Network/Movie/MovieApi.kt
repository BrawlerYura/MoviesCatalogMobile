package com.example.mobile_moviescatalog2023.Network.Movie

import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MoviesModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("api/movies/{page}")
    suspend fun getMovies(@Path("page") page: Int): MoviesModel

    @GET("api/movies/details/{id}")
    suspend fun getDetails(@Path("id") id: String): MovieDetailsModel
}