package com.example.mobile_moviescatalog2023.domain.UseCases.ReviewUseCases

import android.util.Log
import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.Network.Review.ReviewRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModifyModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddReviewUseCase(
    private val repository: ReviewRepository
) {
    suspend fun invoke(reviewModifyModel: ReviewModifyModel, filmId: String): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(repository.addReview(reviewModifyModel, filmId)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}