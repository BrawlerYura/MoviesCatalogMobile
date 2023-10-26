package com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies

@Suppress("PLUGIN_IS_NOT_ENABLED")
@kotlinx.serialization.Serializable
data class RegisterRequestBody (
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)