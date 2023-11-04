package com.example.mobile_moviescatalog2023.Network.User

import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserApi {
    @GET("api/account/profile")
    suspend fun getProfile(): ProfileModel

    @PUT("api/account/profile")
    suspend fun putProfile(@Body body: ProfileModel)
}