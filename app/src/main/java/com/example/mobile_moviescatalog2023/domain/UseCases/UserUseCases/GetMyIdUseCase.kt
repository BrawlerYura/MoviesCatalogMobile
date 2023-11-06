package com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases

import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import kotlinx.coroutines.flow.Flow

class GetMyIdUseCase(
    private val repository: UserRepository
) {
    suspend fun invoke(): Result<ProfileModel> {
        return repository.getProfile()
    }
}