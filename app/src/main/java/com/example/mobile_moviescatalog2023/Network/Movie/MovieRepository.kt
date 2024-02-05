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
    suspend fun getMovies(page: Int): MoviesModel {
        return api.getMovies(page)
    }

    suspend fun getMovieDetails(id: String): MovieDetailsModel {
        return api.getDetails(id)
    }
}