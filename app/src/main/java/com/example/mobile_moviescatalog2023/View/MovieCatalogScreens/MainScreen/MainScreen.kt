package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenContract
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

@Composable
fun MovieListScreen(
    state: MainScreenContract.State,
    onEventSent: (event: MainScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: MainScreenContract.Effect.Navigation) -> Unit
) {
    val lazyListState = rememberLazyListState()

    if (
        !lazyListState.canScrollForward &&
        (state.currentMoviePage - 1 < state.pageCount) &&
        !state.isUpdatingList
        ) {
        onEventSent(MainScreenContract.Event.UpdateMoviesList)
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Carousel(state, onNavigationRequested)
        }
        item {
            Text(
                text = stringResource(R.string.catalog),
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(
                    bottom = 15.dp,
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            )
        }
        items(state.movieList) {
            FilmCard(it, onNavigationRequested)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilmCard(
    item: MovieElementModel,
    onNavigationRequested: (navigationEffect: MainScreenContract.Effect.Navigation) -> Unit
    ) {
    Row(
        modifier = Modifier
        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp).fillMaxWidth().height(130.dp)
            .clickable {
                onNavigationRequested(MainScreenContract.Effect.Navigation.ToFilm(item.id))
            }
    ) {
        Box(modifier = Modifier.fillMaxHeight().width(95.dp)) {
            AsyncImage(
                model = item.poster,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(3.dp)),
                contentScale = ContentScale.Crop
            )
            val filmRating: FilmRating? = calculateFilmRating(item.reviews)
            if(filmRating != null) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.TopStart)
                        .height(20.dp)
                        .width(37.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = filmRating.color)
                ) {
                    Text(
                        text = filmRating.rating,
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W700,
                            fontSize = 13.sp,
                            color = Color(0xFF1D1D1D)
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(start = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth()
            ) {
                Text(
                    text = item.name ?: "",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.align(Alignment.TopStart)
                )

//                Box(
//                    modifier = Modifier
//                        .height(26.dp)
//                        .clip(RoundedCornerShape(35.dp))
//                        .background(Color.Green)
//                        .align(Alignment.TopEnd)
//                ) {
//                    Row(
//                        horizontalArrangement = Arrangement.spacedBy(4.dp),
//                        modifier = Modifier.padding(4.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            painterResource(R.drawable.small_star),
//                            null
//                        )
//                        Text(
//                            text = "10",
//                            style = TextStyle(
//                                fontFamily = interFamily,
//                                fontWeight = FontWeight.W500,
//                                fontSize = 15.sp,
//                                color = MaterialTheme.colorScheme.onBackground,
//                                textAlign = TextAlign.Center
//                            )
//                        )
//                    }
//                }
            }
            Text(
                text = item.year.toString() + " · " + item.country,
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Box(modifier = Modifier.fillMaxSize()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxSize()
                    ) {
                    val itemModifier = Modifier
                        .padding(bottom = 4.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                    item.genres?.take(4)?.forEach {
                        Box(
                            modifier = itemModifier
                        ) {
                            Text(
                                text = it.name ?: "",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun calculateFilmRating(reviews: List<ReviewShortModel>?): FilmRating? {
    if(reviews == null) {
        return null
    } else {

        var sumScore: Int = 0
        reviews.forEach {
            sumScore += it.rating
        }

        val rating = (sumScore.toDouble() / reviews.count())

        val color = when {
            rating >= 0.0 && rating < 3.0 -> {
                Color(0xFFE64646)
            }
            rating >= 3.0 && rating < 4.0 -> {
                Color(0xFFF05C44)
            }
            rating >= 4.0 && rating < 5.0 -> {
                Color(0xFFFFA000)
            }
            rating >= 5.0 && rating < 7.0 -> {
                Color(0xFFFFD54F)
            }
            rating >= 7.0 && rating < 9.0 -> {
                Color(0xFFA3CD4A)
            }
            else -> {
                Color(0xFF4BB34B)
            }
        }

        return FilmRating(
            rating = if (rating != 10.0)
            { rating.toString().substring(startIndex = 0, endIndex = 3) }
            else { rating.toString().substring(startIndex = 0, endIndex = 4) },
            color = color
        )
    }
}

data class FilmRating (
    val rating: String,
    val color: Color
)