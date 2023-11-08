package com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases

import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PutProfileUseCase(
    private val repository: UserRepository
) {
    suspend fun invoke(profileModel: ProfileModel): Result<Unit> {
        return try {
            Result.success(repository.putProfile(profileModel))
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }
}