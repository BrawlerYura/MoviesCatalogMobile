package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.Composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.movieElementModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ThreeFavoriteMovies

@Composable
fun FavoriteFilmCard(
    item: ThreeFavoriteMovies,
    firstMyRating: FilmRating?,
    secondMyRating: FilmRating?,
    thirdMyRating: FilmRating?,
    onNavigationRequested: (id: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Absolute.spacedBy(20.dp)
    ) {
        if (item.firstMovie != null) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                FavoriteMovieCard(
                    item = item.firstMovie,
                    onNavigationRequested = onNavigationRequested,
                    myRating = firstMyRating,
                    index = 1
                )
                if (item.secondMovie != null) {
                    FavoriteMovieCard(
                        item = item.secondMovie,
                        onNavigationRequested = onNavigationRequested,
                        myRating = secondMyRating,
                        index = 2
                    )
                }
            }
        }
        if (item.thirdMovie != null) {
            FavoriteMovieCard(
                item = item.thirdMovie,
                onNavigationRequested = onNavigationRequested,
                myRating =
                if (item.firstMovie == null && item.secondMovie == null) {
                    firstMyRating
                } else {
                    thirdMyRating
                },
                index = 3
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun FavoriteFilmCardPreview() {

    FavoriteFilmCard(
        item = ThreeFavoriteMovies(
            firstMovie = movieElementModel,
            secondMovie = movieElementModel,
            thirdMovie = movieElementModel
        ),
        firstMyRating = null,
        secondMyRating = null,
        thirdMyRating = null,
        onNavigationRequested = { }
    )
}