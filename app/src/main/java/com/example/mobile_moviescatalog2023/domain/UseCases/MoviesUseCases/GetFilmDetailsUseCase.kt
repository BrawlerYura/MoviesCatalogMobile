package com.example.mobile_moviescatalog2023.domain.UseCases.MoviesUseCases

import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class GetFilmDetailsUseCase(
    private val repository: MovieRepository
) {
    suspend fun invoke(id: String): Flow<Result<MovieDetailsModel>> = flow {
        try {
            emit(Result.success(repository.getMovieDetails(id)))
        } catch (e: java.lang.Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}