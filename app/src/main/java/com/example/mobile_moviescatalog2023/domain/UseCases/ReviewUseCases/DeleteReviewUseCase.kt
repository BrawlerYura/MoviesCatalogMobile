package com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases

import com.example.mobile_moviescatalog2023.Network.Review.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteReviewUseCase(
    private val repository: ReviewRepository
) {
    suspend fun invoke(filmId: String, reviewId: String): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(repository.deleteReview(filmId, reviewId)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}