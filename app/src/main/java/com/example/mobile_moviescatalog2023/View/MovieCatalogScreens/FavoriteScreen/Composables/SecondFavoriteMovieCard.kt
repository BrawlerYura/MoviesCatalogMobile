package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun SecondFavoriteMovieCard(
    item: MovieElementModel,
    onNavigationRequested: (id: String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Absolute.spacedBy(5.dp),
        modifier = Modifier.clickable {
            onNavigationRequested(item.id)
        }
            .fillMaxWidth()
            .padding(end = 16.dp)
    ) {
        AsyncImage(
            model = item.poster,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(205.dp)
                .clip(RoundedCornerShape(3.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = item.name ?: "",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondFavoriteMovieCardPreview() {
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
    SecondFavoriteMovieCard(
        item = movieElementModel,
        onNavigationRequested = { }
    )
}