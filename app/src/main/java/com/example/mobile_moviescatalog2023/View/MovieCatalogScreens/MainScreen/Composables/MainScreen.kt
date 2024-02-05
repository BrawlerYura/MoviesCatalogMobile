package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.View.Common.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.Common.NetworkErrorScreen
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.mainStatePreview
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenContract.State,
    onEventSent: (event: MainScreenContract.Event) -> Unit,
    effectFlow: Flow<MainScreenContract.Effect>?,
    onNavigationRequested: (navigationEffect: MainScreenContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is MainScreenContract.Effect.Navigation.ToFilm -> onNavigationRequested(effect)
                is MainScreenContract.Effect.Navigation.ToFavorite -> onNavigationRequested(effect)
                is MainScreenContract.Effect.Navigation.ToProfile -> onNavigationRequested(effect)
                is MainScreenContract.Effect.Navigation.ToIntroducing -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }

    val refreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = {
            onEventSent(MainScreenContract.Event.RefreshMovies)
        }
    )

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    FilmusTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onNavigationToMainRequested = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(index = 0)
                        }
                    },
                    onNavigationToProfileRequested = { onEventSent(MainScreenContract.Event.NavigationToProfile) },
                    onNavigationToFavoriteRequested = { onEventSent(MainScreenContract.Event.NavigationToFavorite) },
                    currentScreen = 0
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .pullRefresh(refreshState)
            ) {
                when {
                    state.isLoaded -> {
                        MovieListScreen(
                            state,
                            lazyListState,
                            { onEventSent(MainScreenContract.Event.UpdateMoviesList) },
                            { id -> onEventSent(MainScreenContract.Event.NavigationToFilm(id)) }
                        )
                    }

                    state.isError -> {
                        NetworkErrorScreen { onEventSent(MainScreenContract.Event.RefreshMovies) }
                    }

                    else -> {
                        MainSkeletonScreen()
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

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {

    MainScreen(
        state = mainStatePreview,
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { }
    )
}