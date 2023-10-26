package com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies

@Suppress("PLUGIN_IS_NOT_ENABLED")
@kotlinx.serialization.Serializable
data class LoginRequestBody(
    val username: String,
    val password: String
)