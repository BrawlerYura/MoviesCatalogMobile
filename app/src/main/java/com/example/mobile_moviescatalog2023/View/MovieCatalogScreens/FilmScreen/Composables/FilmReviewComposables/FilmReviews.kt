package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.UserShortModel
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography

@Composable
fun FilmReviews(
    reviews: List<ReviewModel>?,
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    filmId: String,
    onImageClicked: (imageUrl: String?) -> Unit
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
                        if (it.author != null) {
                            try {
                                it.author.userId == Network.userId
                            } catch (e: Exception) {
                                false
                            }
                        } else {
                            false
                        }
                    )
                }

                CurrentReviewHeader(
                    it,
                    isMyReview.value,
                    state,
                    onEventSent,
                    filmId,
                    onImageClicked
                )

                Text(
                    text = it.reviewText ?: "",
                    style = MyTypography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = it.createDateTime,
                    style = MyTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Start,
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
        filmId = "",
        onImageClicked = { }
    )
}