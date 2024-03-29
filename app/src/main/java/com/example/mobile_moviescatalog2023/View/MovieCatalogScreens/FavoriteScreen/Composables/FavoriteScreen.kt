package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.Composables

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.View.Common.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.Common.NetworkErrorScreen
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.movieElementModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenContract
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ThreeFavoriteMovies
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    state: FavoriteScreenContract.State,
    onEventSent: (event: FavoriteScreenContract.Event) -> Unit,
    effectFlow: Flow<FavoriteScreenContract.Effect>?,
    onNavigationRequested: (navigationEffect: FavoriteScreenContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is FavoriteScreenContract.Effect.Navigation.ToFilm -> onNavigationRequested(effect)
                is FavoriteScreenContract.Effect.Navigation.ToMain -> onNavigationRequested(effect)
                is FavoriteScreenContract.Effect.Navigation.ToProfile -> onNavigationRequested(
                    effect
                )

                is FavoriteScreenContract.Effect.Navigation.ToIntroducing -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    FilmusTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onNavigationToMainRequested = {
                        onEventSent(FavoriteScreenContract.Event.NavigationToMain)
                    },
                    onNavigationToProfileRequested = {
                        onEventSent(FavoriteScreenContract.Event.NavigationToProfile)
                    },
                    onNavigationToFavoriteRequested = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(index = 0)
                        }
                    },
                    currentScreen = 1
                )
            }
        ) {
            val refreshState = rememberPullRefreshState(
                refreshing = state.isRefreshing,
                onRefresh = {
                    onEventSent(FavoriteScreenContract.Event.RefreshScreen)
                }
            )
            Box(
                modifier = Modifier
                    .padding(it)
                    .pullRefresh(refreshState)
            ) {
                when {
                    state.isLoaded -> {
                        FavoriteMoviesListScreen(state, onEventSent, lazyListState)
                    }

                    state.isError -> {
                        NetworkErrorScreen {
                            onEventSent(FavoriteScreenContract.Event.RefreshScreen)
                        }
                    }

                    else -> {
                        FavoriteFilmsPlaceholder()
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
}

@Composable
fun FavoriteMoviesListScreen(
    state: FavoriteScreenContract.State,
    onEventSent: (event: FavoriteScreenContract.Event) -> Unit,
    lazyListState: LazyListState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = spacedBy(20.dp),
        state = lazyListState
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth())
            {
                Text(
                    text = stringResource(R.string.favorite),
                    style =
                    TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 16.dp)
                )
            }
        }
        if (state.favoriteMovieList?.isEmpty() == false) {
            itemsIndexed(state.favoriteMovieList) { index, item ->
                FavoriteFilmCard(
                    item = item,
                    firstMyRating =
                    try {
                        state.myRating[index * 3]
                    } catch (e: Exception) {
                        null
                    },
                    secondMyRating =
                    try {
                        state.myRating[index * 3 + 1]
                    } catch (e: Exception) {
                        null
                    },
                    thirdMyRating =
                    try {
                        state.myRating[index * 3 + 2]
                    } catch (e: Exception) {
                        null
                    },
                ) { id ->
                    onEventSent(
                        FavoriteScreenContract.Event.NavigationToFilm(
                            id
                        )
                    )
                }
            }
        } else {
            item {
                FavoriteFilmsPlaceholder()
            }
        }
        item {
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteScreenPreview() {
    FavoriteScreen(
        state = FavoriteScreenContract.State(
            favoriteMovieList = listOf(
                ThreeFavoriteMovies(
                    firstMovie = movieElementModel,
                    secondMovie = movieElementModel,
                    thirdMovie = movieElementModel
                ),
                ThreeFavoriteMovies(
                    firstMovie = null,
                    secondMovie = null,
                    thirdMovie = movieElementModel
                ),
            ),
            isLoaded = true,
            isError = false,
            isRefreshing = false,
            myRating = listOf()
        ),
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { }
    )
}