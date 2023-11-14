package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Common.MyRatingCard
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.mainStatePreview
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.movieElementModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.domain.Entities.Models.GenreModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilmCard(
    item: MovieElementModel,
    index: Int,
    state: MainScreenContract.State,
    onNavigationRequested: (id: String) -> Unit
) {

    val filmRating: FilmRating? = remember {
        try {
            state.filmRatingsList[index]
        } catch (e: Exception) {
            null
        }
    }
    val myRating: FilmRating? = remember {
        try {
            state.myRating[index]
        } catch (e: Exception) {
            null
        }
    }

    Row(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp).fillMaxWidth()
            .clickable {
                onNavigationRequested(item.id)
            }
    ) {
        Box(modifier = Modifier.width(95.dp).height(130.dp)) {
            AsyncImage(
                model = item.poster,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(3.dp)),
                contentScale = ContentScale.Crop
            )

            if(filmRating != null) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = filmRating.color)
                ) {
                    Text(
                        text = filmRating.rating,
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W700,
                            fontSize = 13.sp,
                            color = Color(0xFF1D1D1D)
                        ),
                        modifier = Modifier.align(Alignment.Center)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(start = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth()
            ) {
                Text(
                    text = item.name ?: "",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.align(Alignment.TopStart).padding(
                        end = if(myRating != null) {
                            50.dp
                        } else {
                            0.dp
                        }
                    )
                )
                if(myRating != null) {
                    MyRatingCard(myRating)
                }
            }
            Text(
                text = item.year.toString() + " · " + item.country,
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    val itemModifier = Modifier
                        .padding(bottom = 4.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                    item.genres?.take(4)?.forEach {
                        Box(
                            modifier = itemModifier
                        ) {
                            Text(
                                text = it.name ?: "",
                                style = TextStyle(
                                    fontFamily = interFamily,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}
data class FilmRating (
    val rating: String,
    val color: Color
)

@Preview(showBackground = true)
@Composable
private fun FilmCardPreview() {
    FilmCard(
        item =  movieElementModel,
        index = 0,
        state = mainStatePreview,
        onNavigationRequested = { }
    )
}