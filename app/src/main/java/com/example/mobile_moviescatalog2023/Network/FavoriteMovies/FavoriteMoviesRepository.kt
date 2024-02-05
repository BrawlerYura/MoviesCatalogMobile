package com.example.mobile_moviescatalog2023.Network.FavoriteMovies

import android.util.Log
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesListModel
import com.example.mobile_moviescatalog2023.Network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class FavoriteMoviesRepository(
    private val api: FavoriteMoviesApi
) {

    suspend fun getFavoriteMovies(): MoviesListModel {
        return api.getFavoriteMovies()
    }

    suspend fun addFavoriteMovie(id: String) {
        api.addFavoriteMovie(id)
    }

    suspend fun deleteFavoriteMovie(id: String) {
        api.deleteFavoriteMovie(id)
    }
}