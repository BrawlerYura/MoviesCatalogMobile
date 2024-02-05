package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables.ReviewDialogComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography

@Composable
fun ReviewDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    filmId: String,
    reviewId: String? = null,
    rating: Int? = null,
    reviewText: String? = null,
    isAnonymous: Boolean? = null
) {
    val isEditing: Boolean = !(rating == null && reviewText == null)
    LaunchedEffect(isEditing) {
        onEventSent(FilmScreenContract.Event.SaveReviewRating(rating ?: 1))
        onEventSent(FilmScreenContract.Event.SaveReviewText(reviewText ?: ""))
        onEventSent(FilmScreenContract.Event.SaveIsAnonymous(isAnonymous ?: false))
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false,
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.Absolute.spacedBy(10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.make_review),
                        style = MyTypography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    RatingBox(state, onEventSent)

                    OutlinedTextField(
                        value = state.myReviewTextField,
                        colors = OutlinedTextFieldDefaults.colors(
                        ),
                        textStyle = MyTypography.bodyMedium,
                        onValueChange = {
                            onEventSent(FilmScreenContract.Event.SaveReviewText(it))
                        },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )

                    IsAnonymousCheckBox(state, onEventSent)

                    Spacer(modifier = Modifier.height(10.dp))

                    ReviewDialogButtons(state, onEventSent, onDismiss, isEditing, filmId, reviewId)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewDialogPreview() {
    ReviewDialog(
        showDialog = true,
        onDismiss = { },
        state = filmScreensPreviewState,
        onEventSent = { },
        filmId = ""
    )
}