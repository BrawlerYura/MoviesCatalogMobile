package com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases

import android.content.Context
import android.util.Log
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager

class LogoutUseCase(
    private val repository: AuthRepository,
    private val context: Context
) {

    suspend fun invoke() {
        try {
            repository.logout()
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
        }
        TokenManager(context).deleteToken()
    }
}