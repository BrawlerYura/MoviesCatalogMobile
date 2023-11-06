package com.example.mobile_moviescatalog2023.Network.Movie

import android.util.Log
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesModel
import com.example.mobile_moviescatalog2023.Network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class MovieRepository(
    private val api: MovieApi
) {
    suspend fun getMovies(page: Int): Result<MoviesModel> {
        return try {
            Result.success(api.getMovies(page))
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMovieDetails(id: String): Result<MovieDetailsModel> {
        return try {
            Result.success(api.getDetails(id))
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }
}