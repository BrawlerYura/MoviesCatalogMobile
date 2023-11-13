package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.UserShortModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables.FilmReviews
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.MainLoadingScreen
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
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
            state.isLoading -> {
                FilmLoadingScreen(state, onEventSent)
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun FilmLoadingScreen(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                { onEventSent(FilmScreenContract.Event.NavigationBack) },
                false,
                state,
                onEventSent
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.Absolute.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(497.dp).shimmerEffect())

            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Box(modifier = Modifier.width(51.dp).height(26.dp).align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(5.dp)).shimmerEffect())
                Box(modifier = Modifier.width(120.dp).height(30.dp).align(Alignment.Center)
                    .clip(RoundedCornerShape(5.dp)).shimmerEffect())
                Box(modifier = Modifier.width(40.dp).height(40.dp).align(Alignment.CenterEnd)
                    .clip(CircleShape).shimmerEffect())
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Absolute.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(140.dp)
                    .clip(RoundedCornerShape(10.dp)).shimmerEffect())

                Box(modifier = Modifier.fillMaxWidth(0.3f).height(20.dp)
                    .clip(RoundedCornerShape(15.dp)).shimmerEffect())
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

    val showName = remember { mutableStateOf(false) }

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

        val lazyListState = rememberLazyListState()

        LaunchedEffect(lazyListState) {
            snapshotFlow { lazyListState.firstVisibleItemIndex }
                .collect { firstVisibleIndex ->
                    showName.value = firstVisibleIndex > 1
                }
        }

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

@Composable
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )

    val gradientColors =
        listOf(
            Color(0xFF292929),
            Color(0xFF2E2E2E),
            Color(0xFF3A3A3A),
            Color(0xFF2E2E2E),
            Color(0xFF292929)
        )

    background(
        brush = Brush.linearGradient(
            colors = gradientColors,
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}