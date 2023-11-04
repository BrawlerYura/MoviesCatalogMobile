package com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases

import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.TokenModel
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.LoginRequestBody
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke(body: LoginRequestBody): Flow<Result<TokenModel>> =
        repository.login(body)
}