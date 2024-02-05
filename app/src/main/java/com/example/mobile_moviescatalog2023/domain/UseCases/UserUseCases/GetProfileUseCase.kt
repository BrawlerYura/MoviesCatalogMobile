package com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases

import android.content.Context
import android.util.Log
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProfileUseCase(
    private val repository: UserRepository
) {

    suspend fun invoke(): Flow<Result<ProfileModel>> = flow {
        try {
            emit(Result.success(repository.getProfile()))
        } catch (e: java.lang.Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}