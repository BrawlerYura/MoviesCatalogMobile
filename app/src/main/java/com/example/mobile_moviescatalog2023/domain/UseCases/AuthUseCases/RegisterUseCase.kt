package com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases

import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.TokenModel
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke(body: RegisterRequestBody): Result<TokenModel> {
        return try {
            Result.success(repository.register(body))
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }
}