package com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases

import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow

class DeleteFavoriteMovieUseCase(
    private val repository: FavoriteMoviesRepository
) {
    suspend fun invoke(id: String): Result<Unit> {
        return try {
            Result.success(repository.deleteFavoriteMovie(id))
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }
}