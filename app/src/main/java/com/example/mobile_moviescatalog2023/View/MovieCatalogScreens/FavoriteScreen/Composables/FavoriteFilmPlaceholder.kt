package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.Composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography

@Composable
fun FavoriteFilmsPlaceholder() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 100.dp)
        .padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(R.string.no_favorite_movies),
            style = MyTypography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(R.string.choose_favorite_film),
            style = MyTypography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteFilmsPlaceholderPreview() {
    FavoriteFilmsPlaceholder()
}