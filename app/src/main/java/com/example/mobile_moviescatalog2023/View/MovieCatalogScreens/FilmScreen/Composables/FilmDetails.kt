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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

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
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W700,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        )

        FilmDetailsYear(movieDetails.year)

        FilmDetailsCountry(movieDetails.country)

        FilmDetailsTagline(movieDetails.tagline)

        FilmDetailsDirector(movieDetails.director)

        FilmDetailsBudget(movieDetails.budget)

        FilmDetailsFees(movieDetails.fees)

        FilmDetailsAgeLimit(movieDetails.ageLimit)

        FilmDetailsTime(movieDetails.time)
    }
}

@Composable
fun FilmDetailsYear(year: Int) {
    Row(horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp)) {
        Text(
            text = stringResource(R.string.year),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        Text(
            text = year.toString(),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FilmDetailsCountry(country: String?) {
    Row(horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp)) {
        Text(
            text = stringResource(R.string.country),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        Text(
            text = country ?: "-",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FilmDetailsTagline(tagline: String?) {
    Row(horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp)) {
        Text(
            text = stringResource(R.string.tagline),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        Text(
            text =
            if(tagline != null && tagline != "-") {
                "\"" + tagline + "\""
            } else {
                "-"
            },
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FilmDetailsDirector(director: String?) {
    Row(horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp)) {
        Text(
            text = stringResource(R.string.director),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        Text(
            text = director ?: "",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FilmDetailsBudget(budget: Int?) {
    Row(horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp)) {
        Text(
            text = stringResource(R.string.budget),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        Text(
            text =
            if(budget != null) {
                "$$budget"
            } else {
                "-"
            },
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FilmDetailsFees(fees: Int?) {
    Row(horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp)) {
        Text(
            text = stringResource(R.string.fees),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        Text(
            text =
            if(fees != null) {
                "$$fees"
            } else {
                "-"
            },
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FilmDetailsAgeLimit(ageLimit: Int) {
    Row(horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp)) {
        Text(
            text = stringResource(R.string.age),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        Text(
            text = "$ageLimit+",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FilmDetailsTime(time: Int) {
    Row(horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp)) {
        Text(
            text = stringResource(R.string.time),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        Text(
            text = time.toString() + " " + stringResource(R.string.minutes),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            ),
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