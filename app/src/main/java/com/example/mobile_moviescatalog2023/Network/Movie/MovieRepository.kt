package com.example.mobile_moviescatalog2023.Network.Movie

import android.util.Log
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MoviesModel
import com.example.mobile_moviescatalog2023.Network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository {
    private val api: MovieApi = Network.getMovieApi()

    suspend fun getMovies(page: Int): Flow<Result<MoviesModel>> = flow {
        try {
            emit(Result.success(api.getMovies(page)))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieDetails(id: String): Flow<Result<MovieDetailsModel>> = flow {
        try {
            emit(Result.success(api.getDetails(id)))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}