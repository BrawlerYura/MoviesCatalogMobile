package com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases

import android.content.Context
import android.util.Log
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import kotlinx.coroutines.flow.first

class GetProfileUseCase(
    private val repository: UserRepository
) {

    suspend fun invoke(): Result<ProfileModel> {
        return try {
            Result.success(repository.getProfile())
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }
}