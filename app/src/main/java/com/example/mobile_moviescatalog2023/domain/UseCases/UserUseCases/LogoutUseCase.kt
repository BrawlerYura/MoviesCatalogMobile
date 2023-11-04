package com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases

import android.content.Context
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager

class LogoutUseCase(
    private val repository: AuthRepository,
    private val context: Context
) {

    suspend fun invoke() {
        repository.logout()
        TokenManager(context).deleteToken()
    }
}