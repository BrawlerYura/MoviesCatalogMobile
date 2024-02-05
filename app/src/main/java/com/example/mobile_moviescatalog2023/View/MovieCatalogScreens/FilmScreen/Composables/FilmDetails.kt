package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography

@Composable
fun FilmAbout(
    movieDetails: MovieDetailsModel
) {
    Column(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 25.dp),
        verticalArrangement = Arrangement.Absolute.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.about_film),
            style = MyTypography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        FilmDetailsRow(
            headerText = stringResource(R.string.year),
            detailsText = movieDetails.year.toString()
        )

        FilmDetailsRow(
            headerText = stringResource(R.string.country),
            detailsText = movieDetails.country ?: "-"
        )

        FilmDetailsRow(
            headerText = stringResource(R.string.tagline),
            detailsText =
            if (movieDetails.tagline != null && movieDetails.tagline != "-") {
                "\"" + movieDetails.tagline + "\""
            } else {
                "-"
            }
        )

        FilmDetailsRow(
            headerText = stringResource(R.string.director),
            detailsText = movieDetails.director ?: "-"
        )

        FilmDetailsRow(
            headerText = stringResource(R.string.budget),
            detailsText = if (movieDetails.budget != null) {
                "$${movieDetails.budget}"
            } else {
                "-"
            }
        )

        FilmDetailsRow(
            headerText = stringResource(R.string.fees),
            detailsText = if (movieDetails.fees != null) {
                "$${movieDetails.fees}"
            } else {
                "-"
            }
        )

        FilmDetailsRow(
            headerText = stringResource(R.string.age),
            detailsText = "${movieDetails.ageLimit}+"
        )

        FilmDetailsRow(
            headerText = stringResource(R.string.time),
            detailsText = movieDetails.time.toString() + " " + stringResource(R.string.minutes)
        )
    }
}

@Composable
fun FilmDetailsRow(headerText: String, detailsText: String) {
    Row(horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp)) {
        Text(
            text = headerText,
            style = MyTypography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        Text(
            text = detailsText,
            style = MyTypography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmAboutPreview() {
    FilmAbout(
        movieDetails = MovieDetailsModel(
            id = "",
            name = null,
            poster = null,
            year = 2000,
            country = "Россия",
            genres = null,
            reviews = null,
            time = 90,
            tagline = "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                    " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            description = null,
            director = "Мистер Лид",
            budget = 5,
            fees = 0,
            ageLimit = 8
        )
    )
}