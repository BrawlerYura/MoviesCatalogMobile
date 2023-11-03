package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.UserShortModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun FilmShortDetails(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp)
    ) {
        val filmRating: FilmRating? = calculateFilmRating(state.movieDetails.reviews)
        if(filmRating != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
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
        Text(
            text = state.movieDetails.name ?: "",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W700,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.align(Alignment.Center).fillMaxWidth().padding(horizontal = 60.dp)
        )

        Box(
            modifier = Modifier.height(40.dp).width(40.dp)
                .clip(CircleShape).align(Alignment.CenterEnd)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    if(state.isMyFavorite) {
                        onEventSent(FilmScreenContract.Event.DeleteFavorite(state.movieDetails.id))
                    } else {
                        onEventSent(FilmScreenContract.Event.AddToFavorite(state.movieDetails.id))
                    }
                },
        ) {
            Icon(
                painter = if(state.isMyFavorite) {
                    painterResource(R.drawable.heart_enabled)
                } else {
                    painterResource(R.drawable.heart_disabled)
                },
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
                tint = if(state.isMyFavorite) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        }
    }
}

private fun calculateFilmRating(reviews: List<ReviewModel>?): FilmRating? {
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

@Preview(showBackground = true)
@Composable
private fun FilmShortDetailsPreview() {
    FilmShortDetails(
        state = filmScreensPreviewState,
        onEventSent = { }
    )
}