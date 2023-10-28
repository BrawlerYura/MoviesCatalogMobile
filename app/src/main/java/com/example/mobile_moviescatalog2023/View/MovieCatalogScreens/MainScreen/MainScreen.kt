package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.BottomNavigationBar
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    state: MainScreenContract.State,
    onEventSent: (event: MainScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: MainScreenContract.Effect.Navigation) -> Unit,
    onBottomNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
) {
    if(state.isRequestingMoviePage) {
        onEventSent(MainScreenContract.Event.GetMovies)
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
            if(!state.isRequestingMoviePage) {
                MovieListScreen(state, onEventSent)
            }
        }
    }
}

@Composable
fun MovieListScreen(
    state: MainScreenContract.State,
    onEventSent: (event: MainScreenContract.Event) -> Unit
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
            Carousel(state)
        }
        item {
            Text(
                text = "Каталог",
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
            FilmCard(it)
        }
        item {
            Spacer(modifier = Modifier.height(68.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilmCard(item: MovieElementModel) {
    Row(
        modifier = Modifier
        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp).fillMaxWidth().height(130.dp)
    ) {
        AsyncImage(
            model = item.poster,
            contentDescription = null,
            modifier = Modifier.fillMaxHeight().width(95.dp).clip(RoundedCornerShape(3.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(start = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Text(
                    text = item.name ?: "",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
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
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
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