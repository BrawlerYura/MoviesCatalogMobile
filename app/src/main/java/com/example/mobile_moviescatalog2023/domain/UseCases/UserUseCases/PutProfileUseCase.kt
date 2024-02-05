package com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases

import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PutProfileUseCase(
    private val repository: UserRepository
) {
    suspend fun invoke(profileModel: ProfileModel): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(repository.putProfile(profileModel)))
        } catch (e: java.lang.Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}