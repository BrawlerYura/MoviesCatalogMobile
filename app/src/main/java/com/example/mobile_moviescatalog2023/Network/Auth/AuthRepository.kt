package com.example.mobile_moviescatalog2023.Network.Auth

import android.util.Log
import com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies.LoginRequestBody
import com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.Network.DataClasses.Responses.TokenResponse
import com.example.mobile_moviescatalog2023.Network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository {
    private val api: AuthApi = Network.getAuthApi()

    suspend fun register(body: RegisterRequestBody): Flow<Result<TokenResponse>> = flow {
        try {
            val tokenData = api.register(body)
            Network.token = tokenData.token
            emit(Result.success(tokenData))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun login(body: LoginRequestBody): Flow<Result<TokenResponse>> = flow {
        try {
            val tokenData = api.login(body)
            Network.token = tokenData.token
            emit(Result.success(tokenData))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun logout() {
        try {
            api.logout()
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
        }
    }
}