package com.example.mobile_moviescatalog2023.Network.DataClasses.Models

data class MovieElementModel(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<GenreModel>?,
    val reviews: List<ReviewShortModel>?
)
