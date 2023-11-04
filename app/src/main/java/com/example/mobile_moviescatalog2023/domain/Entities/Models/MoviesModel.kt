package com.example.mobile_moviescatalog2023.domain.Entities.Models

data class MoviesModel(
    val pageInfo: PageInfoModel,
    val movies: List<MovieElementModel>
)