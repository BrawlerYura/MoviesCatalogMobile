package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.GenreModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilmGenres(
    movieGenres: List<GenreModel>?
) {
    if(movieGenres != null) {
        Column(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 20.dp),
            verticalArrangement = Arrangement.Absolute.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.genres),
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W700,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val itemModifier = Modifier
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.primary)
                movieGenres.forEach {
                    Box(
                        modifier = itemModifier
                    ) {
                        Text(
                            text = it.name ?: "",
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.W500,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmGenresPreview() {
    val genres = listOf(
        GenreModel(
            id = "",
            name = "боевик"
        ),
        GenreModel(
            id = "",
            name = "приключения"
        )
    )
    FilmGenres(
        movieGenres = genres
    )
}