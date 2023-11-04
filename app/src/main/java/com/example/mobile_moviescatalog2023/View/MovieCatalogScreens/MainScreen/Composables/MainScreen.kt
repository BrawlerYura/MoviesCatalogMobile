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
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun MainScreen(
    state: MainScreenContract.State,
    onEventSent: (event: MainScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: MainScreenContract.Effect.Navigation) -> Unit,
    onBottomNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(true) {
        if(state.movieList.isEmpty()) {
            onEventSent(MainScreenContract.Event.GetMovies)
        }
    }

    FilmusTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onBottomNavigationRequested,
                    0
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                if (!state.isRequestingMoviePage) {
                    MovieListScreen(state, onEventSent, onNavigationRequested)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
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
    MainScreen(
        state = MainScreenContract.State (
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
        ),
        onEventSent = { },
        onNavigationRequested = { },
        onBottomNavigationRequested = { }
    )
}