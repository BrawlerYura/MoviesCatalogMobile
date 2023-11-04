package com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases

import android.content.Context
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetProfileUseCase(
    private val repository: UserRepository,
    context: Context
) {
    private val dataStore = TokenManager(context)

    suspend fun invoke(): Flow<Result<ProfileModel>> {

        val tokenValue = dataStore.getToken.first()
        Network.token = tokenValue.toString()

        return repository.getProfile()
    }
}