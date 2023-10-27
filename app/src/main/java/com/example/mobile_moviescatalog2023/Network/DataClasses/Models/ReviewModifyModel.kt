package com.example.mobile_moviescatalog2023.Network.DataClasses.Models

data class ReviewModifyModel(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)