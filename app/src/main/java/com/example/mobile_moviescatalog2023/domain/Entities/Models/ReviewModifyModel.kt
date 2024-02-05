package com.example.mobile_moviescatalog2023.domain.Entities.Models

data class ReviewModifyModel(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)