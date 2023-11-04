package com.example.mobile_moviescatalog2023.domain.Entities.Models

data class ReviewModel(
    val id: String,
    val rating: Int,
    val reviewText: String? = null,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: UserShortModel
)
