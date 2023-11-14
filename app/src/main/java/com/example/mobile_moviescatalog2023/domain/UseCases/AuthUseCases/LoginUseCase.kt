package com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases

import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.TokenModel
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.LoginRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke(body: LoginRequestBody): Flow<Result<TokenModel>> = flow {
        try {
            emit(Result.success(repository.login(body)))
        } catch (e: java.lang.Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}