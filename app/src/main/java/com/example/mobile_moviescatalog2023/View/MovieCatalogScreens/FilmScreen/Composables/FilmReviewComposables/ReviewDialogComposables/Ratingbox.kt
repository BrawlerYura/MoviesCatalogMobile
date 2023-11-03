package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables.ReviewDialogComposables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract

@Composable
fun RatingBox(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        for(i in 1..10) {
            Icon(
                painter = painterResource(
                    if(i <= state.myRating){
                        R.drawable.raiting_star_enabled
                    } else {
                        R.drawable.raiting_star_disabled
                    }
                ),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onEventSent(FilmScreenContract.Event.SaveReviewRating(i))
                },
                tint =
                if(i <= state.myRating){
                    Color(0xFFFFD54F)
                } else {
                    Color(0xFF909499)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingBoxPreview() {
    RatingBox(
        state = filmScreensPreviewState,
        onEventSent = { }
    )
}