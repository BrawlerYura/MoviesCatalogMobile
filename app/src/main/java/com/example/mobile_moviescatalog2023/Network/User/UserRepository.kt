package com.example.mobile_moviescatalog2023.Network.User

import android.util.Log
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ProfileModel
import com.example.mobile_moviescatalog2023.Network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class UserRepository(
    private val api: UserApi
) {
    suspend fun getProfile(): ProfileModel {
        return api.getProfile()
    }

    suspend fun putProfile(body: ProfileModel) {
        api.putProfile(body)
    }
}