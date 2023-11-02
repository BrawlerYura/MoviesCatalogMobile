package com.example.mobile_moviescatalog2023.Network.Review

import android.util.Log
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MoviesModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.Network.Movie.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReviewRepository(
    private val api: ReviewApi
) {
    suspend fun addReview(reviewModifyModel: ReviewModifyModel, movieId: String) = flow {
        try {
            emit(Result.success(api.addReview(reviewModifyModel, movieId)))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun editReview(reviewModifyModel: ReviewModifyModel, movieId: String, id: String) = flow {
        try {
            emit(Result.success(api.editReview(reviewModifyModel, movieId, id)))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteReview(reviewModifyModel: ReviewModifyModel, movieId: String, id: String) = flow {
        try {
            emit(Result.success(api.deleteReview(reviewModifyModel, movieId, id)))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}