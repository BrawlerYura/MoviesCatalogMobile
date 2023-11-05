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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilmCard(
    item: MovieElementModel,
    filmRating: FilmRating?,
    onNavigationRequested: (id: String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp).fillMaxWidth().height(130.dp)
            .clickable {
                onNavigationRequested(item.id)
            }
    ) {
        Box(modifier = Modifier.fillMaxHeight().width(95.dp)) {
            AsyncImage(
                model = item.poster,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(3.dp)),
                contentScale = ContentScale.Crop
            )

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

@Preview(showBackground = true)
@Composable
private fun FilmCardPreview() {
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

    FilmCard(
        item =  movieElementModel,
        filmRating = FilmRating(
            rating = "9.2",
            color = Color.Green
        ),
        onNavigationRequested = { }
    )
}