package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun FavoriteScreen(
    state: FavoriteScreenContract.State,
    onEventSent: (event: FavoriteScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: FavoriteScreenContract.Effect.Navigation) -> Unit,
    onBottomNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(true) {
        onEventSent(FavoriteScreenContract.Event.GetFavoriteMovies)
    }

    FilmusTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onBottomNavigationRequested,
                    1
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
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
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    if (state.favoriteMovieList?.isEmpty() == false) {
                        items(state.favoriteMovieList) {
                            FavoriteFilmCard(it, onNavigationRequested)
                        }
                    } else {
                        item {
                            Column(modifier = Modifier.fillMaxWidth().padding(top = 100.dp)) {
                                Text(
                                    text = stringResource(R.string.no_favorite_movies),
                                    style = TextStyle(
                                        fontFamily = interFamily,
                                        fontWeight = FontWeight.W700,
                                        fontSize = 23.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )

                                Text(
                                    text = stringResource(R.string.choose_favorite_film),
                                    style = TextStyle(
                                        fontFamily = interFamily,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                        .padding(top = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteFilmCard(
    item: ThreeFavoriteMovies,
    onNavigationRequested: (navigationEffect: FavoriteScreenContract.Effect.Navigation) -> Unit
    ) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = spacedBy(20.dp)
    ) {
        if(item.firstMovie != null) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = spacedBy(5.dp),
                    modifier = Modifier.clickable {
                        onNavigationRequested(FavoriteScreenContract.Effect.Navigation.ToFilm(item.firstMovie.id))
                    }
                ) {
                    AsyncImage(
                        model = item.firstMovie.poster,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(0.5f).height(205.dp)
                            .padding(end = 15.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = item.firstMovie.name ?: "",
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W500,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        ),
                    )
                }
                if (item.secondMovie != null) {
                    Column(
                        verticalArrangement = spacedBy(5.dp),
                        modifier = Modifier.clickable {
                            onNavigationRequested(FavoriteScreenContract.Effect.Navigation.ToFilm(item.secondMovie.id))
                        }
                    ) {
                        AsyncImage(
                            model = item.secondMovie.poster,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().height(205.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = item.secondMovie.name ?: "",
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.W500,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            ),
                        )
                    }
                }
            }
        }
        if (item.thirdMovie != null) {
            Column(
                verticalArrangement = spacedBy(5.dp),
                modifier = Modifier.clickable {
                    onNavigationRequested(FavoriteScreenContract.Effect.Navigation.ToFilm(item.thirdMovie.id))
                }
            ) {
                AsyncImage(
                    model = item.thirdMovie.poster,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(205.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = item.thirdMovie.name ?: "",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}