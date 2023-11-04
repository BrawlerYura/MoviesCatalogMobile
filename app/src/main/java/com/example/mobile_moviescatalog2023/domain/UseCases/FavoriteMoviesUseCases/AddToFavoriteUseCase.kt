package com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases

import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import kotlinx.coroutines.flow.Flow
class AddToFavoriteUseCase(
    private val repository: FavoriteMoviesRepository
) {
    suspend fun invoke(id: String): Flow<Result<Unit>> =
        repository.addFavoriteMovie(id)
}