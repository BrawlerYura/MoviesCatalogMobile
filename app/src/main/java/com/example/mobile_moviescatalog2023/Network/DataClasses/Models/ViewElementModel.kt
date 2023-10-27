package com.example.mobile_moviescatalog2023.Network.DataClasses.Models

data class ViewElementModel(
    val id: String,
    val name: String? = null,
    val poster: String? = null,
    val year: Int,
    val country: String? = null,
    val genres: GenreModel? = null,
    val reviews: ReviewShortModel? = null
)
