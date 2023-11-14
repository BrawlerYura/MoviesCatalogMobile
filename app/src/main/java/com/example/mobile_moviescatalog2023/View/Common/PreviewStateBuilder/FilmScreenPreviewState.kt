package com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder

import androidx.compose.ui.graphics.Color
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.UserShortModel

val filmScreensPreviewState = FilmScreenContract.State (
    isLoaded = true,
    isError = false,
    isRefreshing = false,
    movieDetails = MovieDetailsModel(
        id = "",
        name = "Тайна кокоё",
        poster = null,
        year = 0,
        country = "Россия",
        genres = listOf(
            GenreModel(
                id = "",
                name = "приключение"
            ),
            GenreModel(
                id = "",
                name = "боевик"
            ),
            GenreModel(
                id = "",
                name = "головоломки"
            )
        ),
        reviews = listOf(
            ReviewModel(
                id = "",
                rating = 9,
                reviewText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                        " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                isAnonymous = true,
                createDateTime = "10.10.2010",
                author = UserShortModel(
                    userId = "",
                    nickName = null,
                    avatar = null
                )
            ),
            ReviewModel(
                id = "",
                rating = 5,
                reviewText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                        " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                isAnonymous = false,
                createDateTime = "10.10.2010",
                author = UserShortModel(
                    userId = "",
                    nickName = "BrawlerYura",
                    avatar = null
                )
            )
        ),
        time = 56,
        tagline = "Lorem ipsum dolor sit amet, consectetur adipiscing elit,",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit,",
        director = "Мистер Лид",
        budget = 5,
        fees = 0,
        ageLimit = 8
    ),
    isAddingSuccess = null,
    isDeletingSuccess = null,
    isMyFavorite = true,
    isWithMyReview = false,
    myReviewTextField = "Lorem ipsum dolor",
    myRating = 4,
    isAnonymous = false,
    currentFilmRating = FilmRating(
        rating = "2.5",
        color = Color.Red
    )
)