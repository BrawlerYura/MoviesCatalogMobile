package com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases

import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import kotlinx.coroutines.flow.Flow

class GetFilmDetailsUseCase(
    private val repository: MovieRepository
) {
    suspend fun invoke(id: String): Flow<Result<MovieDetailsModel>> =
        repository.getMovieDetails(id)
}