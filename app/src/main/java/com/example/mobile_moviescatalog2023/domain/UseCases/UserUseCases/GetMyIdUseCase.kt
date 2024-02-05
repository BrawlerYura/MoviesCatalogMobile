package com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases

import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetMyIdUseCase(
    private val repository: UserRepository
) {
    suspend fun invoke(): Flow<Result<ProfileModel>> = flow{
        try {
            emit(Result.success(repository.getProfile()))
        } catch (e: java.lang.Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}