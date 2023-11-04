package com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases

import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesModel
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend fun invoke(page: Int): Flow<Result<MoviesModel>> =
        repository.getMovies(page)
}