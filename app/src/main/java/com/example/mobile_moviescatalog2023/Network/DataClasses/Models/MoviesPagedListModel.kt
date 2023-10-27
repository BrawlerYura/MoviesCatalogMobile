package com.example.mobile_moviescatalog2023.Network.DataClasses.Models

data class MoviesPagedListModel(
    val movies: MovieElementModel? = null,
    val pageInfo: PageInfoModel
)
