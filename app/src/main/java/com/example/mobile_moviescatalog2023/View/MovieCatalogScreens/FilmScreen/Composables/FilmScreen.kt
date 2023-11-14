package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.View.Common.NetworkErrorScreen
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.UserShortModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables.FilmReviews
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
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
                is FilmScreenContract.Effect.Navigation.ToIntroducing -> onNavigationRequested(effect)
            }
        }?.collect()
    }
    val refreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = {
            onEventSent(FilmScreenContract.Event.RefreshScreen(filmId))
        }
    )

    FilmusTheme {
        Box(modifier = Modifier.fillMaxSize().pullRefresh(refreshState)) {
            when {
                state.isLoaded -> {
                    FilmLoadedScreen(state, onEventSent, filmId)
                }

                state.isError -> {
                    NetworkErrorScreen { onEventSent(FilmScreenContract.Event.RefreshScreen(filmId)) }
                }

                else -> {
                    FilmSkeletonScreen(state, onEventSent)
                }
            }
            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = refreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
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