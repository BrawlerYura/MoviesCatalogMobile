package com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases

import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesListModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetFavoriteMoviesUseCase(
    private val repository: FavoriteMoviesRepository
) {
    suspend fun invoke(): Result<MoviesListModel> {
        return try {
            Result.success(repository.getFavoriteMovies())
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }
}