package com.example.mobile_moviescatalog2023.Network.DataClasses.Models

data class MoviesModel(
    val pageInfo: PageInfoModel,
    val movies: List<MovieElementModel>
)