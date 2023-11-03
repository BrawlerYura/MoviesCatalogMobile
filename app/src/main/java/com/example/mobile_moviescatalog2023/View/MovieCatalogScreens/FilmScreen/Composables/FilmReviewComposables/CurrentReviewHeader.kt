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
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.UserShortModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables.ReviewDialogComposables.ReviewDialog
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun CurrentReviewHeader(
    it: ReviewModel,
    isMyReview: Boolean,
    state: FilmScreenContract.State,
    onEventSent: (FilmScreenContract.Event) -> Unit,
    filmId: String
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = if(!it.isAnonymous || isMyReview) {
                    it.author.avatar ?: R.drawable.logo
                } else {
                    R.drawable.logo
                       },
                contentDescription = null,
                modifier = Modifier.size(40.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    verticalArrangement = Arrangement.Absolute.spacedBy(2.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.align(Alignment.TopStart).padding(end = 100.dp)
                ) {
                    Text(
                        text = if(!it.isAnonymous || isMyReview) {
                            it.author.nickName ?: ""
                        } else {
                            stringResource(R.string.anonymous_name)
                               },
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W500,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (isMyReview) {
                        Text(
                            text = if (!it.isAnonymous) {
                                stringResource(R.string.my_review)
                            } else {
                                stringResource(R.string.my_anonymous_review)
                            },
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
                Box(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(
                                end = if (isMyReview) {
                                    36.dp
                                } else {
                                    0.dp
                                }
                            )
                            .height(26.dp)
                            .clip(RoundedCornerShape(35.dp))
                            .background(calculateRatingColor(it.rating))
                            .align(Alignment.TopEnd)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Absolute.spacedBy(4.dp),
                            modifier = Modifier.padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(R.drawable.small_star),
                                null
                            )
                            Text(
                                text = it.rating.toString(),
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.W500,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                    if (isMyReview) {
                        var showDialog by remember { mutableStateOf(false) }

                        ReviewDialog(
                            showDialog,
                            onDismiss = { showDialog = !showDialog },
                            state,
                            onEventSent,
                            filmId,
                            reviewId = it.id,
                            rating = it.rating,
                            reviewText = it.reviewText,
                            isAnonymous = it.isAnonymous
                        )

                        var expanded by remember { mutableStateOf(false) }

                        Box(
                            modifier = Modifier
                                .height(26.dp).width(26.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onSurface)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    expanded = !expanded
                                }
                        ) {
                            Icon(
                                painterResource(R.drawable.dots),
                                null,
                                Modifier.align(Alignment.Center)
                            )
                        }
                        Menu(
                            expanded = expanded,
                            onDismiss = { expanded = !expanded },
                            onEditRequested = { showDialog = !showDialog },
                            onDeleteRequested = {
                                onEventSent(FilmScreenContract.Event.DeleteMyReview(filmId, it.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

fun calculateRatingColor(rating: Int): Color {
    return when(rating) {
        in 0 until 3 -> {
            Color(0xFFE64646)
        }
        in 3 until 4 -> {
            Color(0xFFF05C44)
        }
        in 4 until 6 -> {
            Color(0xFFFFA000)
        }
        in 6 until 8 -> {
            Color(0xFFFFD54F)
        }
        in 8 until 9 -> {
            Color(0xFFA3CD4A)
        }
        else -> {
            Color(0xFF4BB34B)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentReviewHeaderPreview() {
    CurrentReviewHeader(
        it = ReviewModel(
            id = "",
            rating = 0,
            reviewText = null,
            isAnonymous = true,
            createDateTime = "10.10.2010",
            author = UserShortModel(
                userId = "",
                nickName = "BrawlerYura",
                avatar = null
            )
        ),
        isMyReview = true,
        state = filmScreensPreviewState,
        onEventSent = { },
        filmId = ""
    )
}