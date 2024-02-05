package com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases

import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.TokenModel
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke(body: RegisterRequestBody): Flow<Result<TokenModel>> = flow {
        try {
            emit(Result.success(repository.register(body)))
        } catch (e: java.lang.Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}