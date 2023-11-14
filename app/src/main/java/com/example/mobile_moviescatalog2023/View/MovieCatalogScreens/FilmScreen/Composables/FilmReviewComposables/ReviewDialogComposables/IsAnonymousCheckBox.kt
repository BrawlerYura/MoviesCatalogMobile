package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmReviewComposables.ReviewDialogComposables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun IsAnonymousCheckBox(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            onEventSent(FilmScreenContract.Event.SaveIsAnonymous(!state.isAnonymous))
        },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = state.isAnonymous,
            onCheckedChange = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(R.string.anonymous_check_box),
            style = MyTypography.labelLarge,
            color =  MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IsAnonymousCheckBoxPreview() {
    IsAnonymousCheckBox(
        state = filmScreensPreviewState,
        onEventSent = { }
    )
}