package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen.SplashContract
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

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
            }
        }?.collect()
    }

    FilmusTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onNavigationToMainRequested = { },
                    onNavigationToProfileRequested = { onEventSent(MainScreenContract.Event.NavigationToProfile) },
                    onNavigationToFavoriteRequested = { onEventSent(MainScreenContract.Event.NavigationToFavorite) },
                    currentScreen = 0
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                when {
                    state.isSuccess -> MovieListScreen(
                        state,
                        { onEventSent(MainScreenContract.Event.UpdateMoviesList) },
                        { id -> onEventSent(MainScreenContract.Event.NavigationToFilm(id)) }
                    )
                }
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
private val movieElementModel =
    MovieElementModel(
        id = "27e0d4f4-6e31-4053-a2be-08d9b9f3d2a2",
        name = "Пираты Карибского моря: Проклятие Черной жемчужины",
        poster = null,
        year = 2003,
        country = "США",
        genres = genres,
        reviews = reviews,
    )

val mainStatePreview = MainScreenContract.State (
    currentMoviePage = 1,
    isRequestingMoviePage = false,
    movieList = listOf(
        movieElementModel
    ),
    movieCarouselList = listOf(
        movieElementModel,
        movieElementModel,
        movieElementModel,
        movieElementModel
    ),
    isSuccess = true,
    pageCount = 1,
    isUpdatingList = false,
    filmRatingsList = listOf()
)