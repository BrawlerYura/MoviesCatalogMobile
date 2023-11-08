package com.example.mobile_moviescatalog2023.Network.Auth

import android.util.Log
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.LoginRequestBody
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.domain.Entities.Models.TokenModel
import com.example.mobile_moviescatalog2023.Network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class AuthRepository(
    private val api: AuthApi
) {
    suspend fun register(body: RegisterRequestBody): TokenModel {
        return api.register(body)
    }

    suspend fun login(body: LoginRequestBody): TokenModel {
        return api.login(body)
    }

    suspend fun logout() {
        api.logout()
    }
}