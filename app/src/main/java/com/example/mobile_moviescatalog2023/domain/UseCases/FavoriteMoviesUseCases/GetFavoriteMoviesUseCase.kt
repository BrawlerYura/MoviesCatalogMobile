package com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases

import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesListModel
import kotlinx.coroutines.flow.Flow

class GetFavoriteMoviesUseCase(
    private val repository: FavoriteMoviesRepository
) {
    suspend fun invoke(): Flow<Result<MoviesListModel>> =
        repository.getFavoriteMovies()
}