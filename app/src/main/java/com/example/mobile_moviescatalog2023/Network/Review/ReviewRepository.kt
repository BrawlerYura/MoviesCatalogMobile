package com.example.mobile_moviescatalog2023.Network.Review

import android.util.Log
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.Network.Movie.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReviewRepository(
    private val api: ReviewApi
) {
    suspend fun addReview(reviewModifyModel: ReviewModifyModel, movieId: String) {
        api.addReview(reviewModifyModel, movieId)
    }

    suspend fun editReview(reviewModifyModel: ReviewModifyModel, movieId: String, id: String) {
        api.editReview(reviewModifyModel, movieId, id)
    }

    suspend fun deleteReview(movieId: String, id: String) {
        api.deleteReview(movieId, id)
    }
}