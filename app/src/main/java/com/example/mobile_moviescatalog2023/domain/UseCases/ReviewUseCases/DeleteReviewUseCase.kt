package com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases

import com.example.mobile_moviescatalog2023.Network.Review.ReviewRepository

class DeleteReviewUseCase(
    private val repository: ReviewRepository
) {
    suspend fun invoke(filmId: String, reviewId: String) {
        repository.deleteReview(filmId, reviewId)
    }
}