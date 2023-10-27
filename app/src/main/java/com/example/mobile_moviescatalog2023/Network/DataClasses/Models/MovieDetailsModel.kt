package com.example.mobile_moviescatalog2023.Network.DataClasses.Models

data class MovieDetailsModel(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: GenreModel?,
    val reviews: ReviewModel?,
    val time: Int,
    val tagline: String?,
    val description: String?,
    val director: String?,
    val budget: Int?,
    val fees: Int?,
    val ageLimit: Int
)
