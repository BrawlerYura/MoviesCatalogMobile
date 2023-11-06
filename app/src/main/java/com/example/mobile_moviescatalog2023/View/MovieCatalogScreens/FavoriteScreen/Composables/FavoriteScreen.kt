package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
 
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ThreeFavoriteMovies
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

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
                is FavoriteScreenContract.Effect.Navigation.ToProfile -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    FilmusTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onNavigationToMainRequested = { onEventSent(FavoriteScreenContract.Event.NavigationToMain) },
                    onNavigationToProfileRequested = { onEventSent(FavoriteScreenContract.Event.NavigationToProfile) },
                    onNavigationToFavoriteRequested = { },
                    currentScreen = 1
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = spacedBy(20.dp)
                ) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth())
                        {
                            Text(
                                text = stringResource(R.string.favorite),
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.align(Alignment.Center).padding(top = 16.dp)
                            )
                        }
                    }
                    if (state.favoriteMovieList?.isEmpty() == false) {
                        items(state.favoriteMovieList) {
                            FavoriteFilmCard(
                                it
                            ) { id -> onEventSent(FavoriteScreenContract.Event.NavigationToFilm(id)) }
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
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteScreenPreview() {
    val genres = listOf(
        GenreModel(
            id = "",
            name = "боевик"
        ),
        GenreModel(
            id = "",
            name = "приключения"
        )
    )

    val reviews = listOf(
        ReviewShortModel(
            id = "",
            rating = 7
        ),
        ReviewShortModel(
            id = "",
            rating = 9
        ),
    )
    val movieElementModel =
        MovieElementModel(
            id = "27e0d4f4-6e31-4053-a2be-08d9b9f3d2a2",
            name = "Пираты Карибского моря: Проклятие Черной жемчужины",
            poster = null,
            year = 2003,
            country = "США",
            genres = genres,
            reviews = reviews,
        )
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
            isSuccess = true
        ),
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { }
    )
}