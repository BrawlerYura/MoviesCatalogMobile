package com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases

import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import kotlinx.coroutines.flow.Flow
class AddToFavoriteUseCase(
    private val repository: FavoriteMoviesRepository
) {
    suspend fun invoke(id: String): Result<Unit> {
        return try {
            Result.success(repository.addFavoriteMovie(id))
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }
}