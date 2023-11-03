package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.GenreModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.ThreeFavoriteMovies
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun FavoriteFilmCard(
    item: ThreeFavoriteMovies,
    onNavigationRequested: (navigationEffect: FavoriteScreenContract.Effect.Navigation) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Absolute.spacedBy(20.dp)
    ) {
        if(item.firstMovie != null) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                FirstFavoriteMovieCard(item.firstMovie, onNavigationRequested)
                if (item.secondMovie != null) {
                    SecondFavoriteMovieCard(item.secondMovie, onNavigationRequested)
                }
            }
        }
        if (item.thirdMovie != null) {
            ThirdFavoriteMovieCard(item.thirdMovie, onNavigationRequested)
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun FavoriteFilmCardPreview() {
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
    FavoriteFilmCard(
        item = ThreeFavoriteMovies(
            firstMovie = movieElementModel,
            secondMovie = movieElementModel,
            thirdMovie = movieElementModel
        ),
        onNavigationRequested = { }
    )
}