package com.example.mobile_moviescatalog2023.Network.DataClasses.Models

data class MovieElementModel(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: GenreModel?,
    val reviews: ReviewShortModel?
)
