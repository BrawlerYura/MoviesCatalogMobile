package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.UserShortModel
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.formatDateFromApi
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun FilmReviews(
    reviews: List<ReviewModel>?,
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    filmId: String
) {
    Column(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 25.dp)
    ) {
        FilmReviewHeader(state, onEventSent, filmId)

        reviews?.forEach {
            Column(
                verticalArrangement = Arrangement.Absolute.spacedBy(8.dp)
            ) {
                val isMyReview = remember {
                    mutableStateOf(
                        try { it.author.userId == Network.userId }
                        catch (e: Exception) {
                            false
                        }
                    )
                }

                CurrentReviewHeader(it, isMyReview.value, state, onEventSent, filmId)

                Text(
                    text = it.reviewText ?: "",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start
                    )
                )

                Text(
                    text = formatDateFromApi(it.createDateTime),
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmReviewsPreview() {
    FilmReviews(
        reviews = listOf(
            ReviewModel(
                id = "",
                rating = 6,
                reviewText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                        " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                isAnonymous = true,
                createDateTime = "10.10.2010",
                author = UserShortModel(
                    userId = "",
                    nickName = null,
                    avatar = null
                )
            )
        ),
        state = filmScreensPreviewState,
        onEventSent = { },
        filmId = ""
    )
}