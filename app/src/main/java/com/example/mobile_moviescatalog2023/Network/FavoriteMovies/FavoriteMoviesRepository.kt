package com.example.mobile_moviescatalog2023.Network.FavoriteMovies

import android.util.Log
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesListModel
import com.example.mobile_moviescatalog2023.Network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavoriteMoviesRepository(
    private val api: FavoriteMoviesApi
) {

    suspend fun getFavoriteMovies(): Flow<Result<MoviesListModel>> = flow {
        try {
            emit(Result.success(api.getFavoriteMovies()))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addFavoriteMovie(id: String) = flow {
        try {
            emit(Result.success(api.addFavoriteMovie(id)))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteFavoriteMovie(id: String) = flow {
        try {
            emit(Result.success(api.deleteFavoriteMovie(id)))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}