package com.example.mobile_moviescatalog2023.Network.Auth

import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.LoginRequestBody
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.domain.Entities.Models.TokenModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/account/register")
    suspend fun register(@Body body: RegisterRequestBody): TokenModel

    @POST("api/account/login")
    suspend fun login(@Body body: LoginRequestBody): TokenModel

    @POST("api/account/logout")
    suspend fun logout()
}