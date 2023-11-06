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
    suspend fun register(body: RegisterRequestBody): Result<TokenModel> {
        return try {
            Result.success(api.register(body))
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(body: LoginRequestBody): Result<TokenModel> {
        return try {
            Result.success(api.login(body))
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }

//    suspend fun register(body: RegisterRequestBody): Flow<Result<TokenModel>> = flow {
//        try {
//            val tokenData = api.register(body)
//            Network.token = tokenData.token
//            emit(Result.success(tokenData))
//        } catch (e: Exception) {
//            Log.e("a", e.message.toString())
//            emit(Result.failure(Throwable(e)))
//        }
//    }.flowOn(Dispatchers.IO)

//    suspend fun login(body: LoginRequestBody): Flow<Result<TokenModel>> = flow {
//        try {
//            val tokenData = api.login(body)
//            Network.token = tokenData.token
//            emit(Result.success(tokenData))
//        } catch (e: Exception) {
//            Log.e("a", e.message.toString())
//            emit(Result.failure(Throwable(e)))
//        }
//    }.flowOn(Dispatchers.IO)
//
    suspend fun logout() {
        try {
            api.logout()
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
        }
    }
}