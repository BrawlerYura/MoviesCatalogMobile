package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.UserShortModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables.FilmReviews
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun FilmScreen(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    effectFlow: Flow<FilmScreenContract.Effect>?,
    onNavigationRequested: (navigationEffect: FilmScreenContract.Effect.Navigation) -> Unit,
    filmId: String
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is FilmScreenContract.Effect.Navigation.Back -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    if(!state.isLoading) {
        onEventSent(FilmScreenContract.Event.LoadFilmDetails(filmId))
    }
    FilmusTheme {
        when {
            state.isLoaded -> {
                FilmLoadedScreen(state, onEventSent, filmId)
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun FilmLoadedScreen(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    filmId: String
) {
    val lazyListState = rememberLazyListState()
    val showName = remember { mutableStateOf(false) }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { firstVisibleIndex ->
                showName.value = firstVisibleIndex > 1
            }
    }

    Scaffold(
        topBar = {
            TopBar(
                { onEventSent(FilmScreenContract.Event.NavigationBack) },
                showName.value,
                state,
                onEventSent
            )
        }
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.padding(it)
        ) {
            item {
                FilmPoster(state.movieDetails.poster, lazyListState)
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

@Preview(showBackground = true)
@Composable
private fun FilmScreenPreview() {
    FilmScreen(
        state = filmScreensPreviewState,
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { },
        filmId = ""
    )
}

val filmScreensPreviewState = FilmScreenContract.State (
    isLoaded = true,
    isLoading = true,
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