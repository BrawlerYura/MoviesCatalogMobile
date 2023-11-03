package com.example.mobile_moviescatalog2023.Network.Review

import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModifyModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewApi {
    @POST("api/movie/{movieId}/review/add")
    suspend fun addReview(@Body body: ReviewModifyModel, @Path("movieId") movieId: String)

    @PUT("api/movie/{movieId}/review/{id}/edit")
    suspend fun editReview(@Body body: ReviewModifyModel, @Path("movieId") movieId: String, @Path("id") id: String)

    @DELETE("api/movie/{movieId}/review/{id}/delete")
    suspend fun deleteReview(@Path("movieId") movieId: String, @Path("id") id: String)
}