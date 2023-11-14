package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.View.Common.MyRatingCard
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.movieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography

@Composable
fun FavoriteMovieCard(
    item: MovieElementModel,
    myRating: FilmRating?,
    index: Int,
    onNavigationRequested: (id: String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Absolute.spacedBy(5.dp),
        modifier = when(index) {
            1 -> {
                Modifier
                    .clickable { onNavigationRequested(item.id) }
                    .fillMaxWidth(0.5f)
                    .padding(start = 16.dp, end = 7.dp)
            }
            2 -> {
                Modifier
                    .clickable { onNavigationRequested(item.id) }
                    .fillMaxWidth()
                    .padding(start = 7.dp, end = 16.dp)
            }
            else -> {
                Modifier
                    .clickable { onNavigationRequested(item.id) }
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(205.dp)
        ) {
            AsyncImage(
                model = item.poster,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(3.dp)),
                contentScale = ContentScale.Crop
            )

            if(myRating != null) {
                MyRatingCard(myRating)
            }
        }
        Text(
            text = item.name ?: "",
            style = MyTypography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FirstFavoriteMovieCardPreview() {
    FavoriteMovieCard(
        item = movieElementModel,
        onNavigationRequested = { },
        myRating = FilmRating(
            rating = 10.toString(),
            color = Color.Green
            ),
        index = 3
    )
}