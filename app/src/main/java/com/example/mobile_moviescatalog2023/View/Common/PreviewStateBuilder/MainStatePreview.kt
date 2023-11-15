package com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder

import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewShortModel

private val genres = listOf(
    GenreModel(
        id = "",
        name = "боевик"
    ),
    GenreModel(
        id = "",
        name = "приключения"
    )
)

private val reviews = listOf(
    ReviewShortModel(
        id = "",
        rating = 7
    ),
    ReviewShortModel(
        id = "",
        rating = 9
    ),
)
private val mainMovieElementModel =
    MovieElementModel(
        id = "27e0d4f4-6e31-4053-a2be-08d9b9f3d2a2",
        name = "Пираты Карибского моря: Проклятие Черной жемчужины",
        poster = null,
        year = 2003,
        country = "США",
        genres = genres,
        reviews = reviews,
    )

val mainStatePreview = MainScreenContract.State(
    currentMoviePage = 1,
    movieList = listOf(
        mainMovieElementModel,
        mainMovieElementModel,
        mainMovieElementModel,
        mainMovieElementModel,
        mainMovieElementModel,
        mainMovieElementModel,
    ),
    isError = true,
    pageCount = 1,
    isUpdatingList = false,
    myRating = listOf(),
    filmRatingsList = listOf(),
    isLoaded = true,
    isRefreshing = false
)