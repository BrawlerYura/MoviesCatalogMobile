package com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases

import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteFavoriteMovieUseCase(
    private val repository: FavoriteMoviesRepository
) {
    suspend fun invoke(id: String): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(repository.deleteFavoriteMovie(id)))
        } catch (e: java.lang.Exception) {
           emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}