package com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases

import com.example.mobile_moviescatalog2023.Network.Review.ReviewRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModifyModel
import kotlinx.coroutines.flow.Flow

class PutReviewUseCase(
    private val repository: ReviewRepository
) {
    suspend fun invoke(reviewModifyModel: ReviewModifyModel, filmId: String, reviewId: String): Result<Unit> {
        return try {
            Result.success(repository.editReview(reviewModifyModel, filmId, reviewId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}