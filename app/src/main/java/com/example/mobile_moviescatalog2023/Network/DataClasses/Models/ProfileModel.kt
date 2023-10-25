package com.example.mobile_moviescatalog2023.Network.DataClasses.Models

data class ProfileModel (
    val id: String,
    val nickName: String? = null,
    val email: String,
    val avatarLink: String? = null,
    val name: String,
    val birthDate: String,
    val gender: Int
)