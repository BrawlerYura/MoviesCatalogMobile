package com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases

import com.example.mobile_moviescatalog2023.Network.Review.ReviewRepository

class DeleteReviewUseCase(
    private val repository: ReviewRepository
) {
    suspend fun invoke(filmId: String, reviewId: String): Result<Unit> {
        return try {
            Result.success(repository.deleteReview(filmId, reviewId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}