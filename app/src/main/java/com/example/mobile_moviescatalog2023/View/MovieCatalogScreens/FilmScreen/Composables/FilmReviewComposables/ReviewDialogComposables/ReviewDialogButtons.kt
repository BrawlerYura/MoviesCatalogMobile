package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables.ReviewDialogComposables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun ReviewDialogButtons(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    onDismiss: () -> Unit,
    isEditing: Boolean,
    filmId: String,
    reviewId: String?
) {
    Button(
        onClick = {
            if(!isEditing) {
                onEventSent(
                    FilmScreenContract.Event.AddMyReview(
                        ReviewModifyModel(
                            reviewText = state.myReviewTextField,
                            rating = state.myRating,
                            isAnonymous = state.isAnonymous
                        ),
                        filmId = filmId
                    )
                )
            } else {
                onEventSent(
                    FilmScreenContract.Event.EditMyReview(
                        ReviewModifyModel(
                            reviewText = state.myReviewTextField,
                            rating = state.myRating,
                            isAnonymous = state.isAnonymous
                        ),
                        filmId = filmId,
                        reviewId = reviewId ?: ""
                    )
                )
            }

            onDismiss()
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Text(
            text = stringResource(R.string.save),
            style = MyTypography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
    }

    Button(
        onClick = { onDismiss() },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Text(
            text = stringResource(R.string.refuse),
            style = MyTypography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewDialogButtonsPreview() {
    ReviewDialogButtons(
        state = filmScreensPreviewState,
        onEventSent = { },
        onDismiss = { },
        isEditing = false,
        filmId = "",
        reviewId = null
    )
}