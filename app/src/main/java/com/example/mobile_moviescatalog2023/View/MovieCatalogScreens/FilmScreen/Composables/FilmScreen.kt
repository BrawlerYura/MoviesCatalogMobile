package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.GenreModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.UserShortModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables.FilmReviews
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun FilmScreen(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: FilmScreenContract.Effect.Navigation) -> Unit,
    filmId: String
) {
    FilmusTheme {
        LaunchedEffect(true) {
            onEventSent(FilmScreenContract.Event.LoadFilmDetails(filmId))
        }
        Scaffold(
            topBar = {
                TopBar(
                    onNavigationRequested
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    FilmPoster(state.movieDetails.poster)
                }
                item {
                    FilmShortDetails(
                        state,
                        onEventSent
                    )
                }
                item {
                    FilmDescription(state.movieDetails.description)
                }
                item {
                    FilmGenres(state.movieDetails.genres)
                }
                item {
                    FilmAbout(state.movieDetails)
                }
                item {
                    FilmReviews(state.movieDetails.reviews, state, onEventSent, filmId)
                }
            }
        }
    }
}

fun formatDateFromApi(date: String): String {
    val parts = date.split("-")
    val year = parts[0]
    val month = parts[1]
    val day = parts[2]
    return "${day.substring(startIndex = 0, endIndex = 2)}.$month.$year"
}

@Preview(showBackground = true)
@Composable
private fun FilmScreenPreview() {
    FilmScreen(
        state = filmScreensPreviewState,
        onEventSent = { },
        onNavigationRequested = { },
        filmId = ""
    )
}

val filmScreensPreviewState = FilmScreenContract.State (
    isSuccess = null,
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
    isAnonymous = false
)