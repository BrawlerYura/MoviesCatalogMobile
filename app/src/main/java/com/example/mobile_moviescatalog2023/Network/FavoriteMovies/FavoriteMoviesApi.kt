package com.example.mobile_moviescatalog2023.Network.FavoriteMovies

import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesListModel
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteMoviesApi {
    @GET("api/favorites")
    suspend fun getFavoriteMovies(): MoviesListModel

    @POST("api/favorites/{id}/add")
    suspend fun addFavoriteMovie(@Path("id") id: String)

    @DELETE("api/favorites/{id}/delete")
    suspend fun deleteFavoriteMovie(@Path("id") id: String)
}