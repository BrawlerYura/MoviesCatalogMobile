package com.example.mobile_moviescatalog2023.Network.User

import android.util.Log
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ProfileModel
import com.example.mobile_moviescatalog2023.Network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val api: UserApi
) {
    suspend fun getProfile() = flow {
        try {
            emit(Result.success(api.getProfile()))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun putProfile(body: ProfileModel) = flow {
        try {
            emit(Result.success(api.putProfile(body)))
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}