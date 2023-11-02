package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.GenreModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.FilmRating
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FilmScreen(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: FilmScreenContract.Effect.Navigation) -> Unit,
    filmId: String
) {
    LaunchedEffect(true) {
        onEventSent(FilmScreenContract.Event.LoadFilmDetails(filmId))
    }
    Scaffold (
        topBar = { TopBar(
            onNavigationRequested
        ) }
    ) {
        if(state.movieDetails != null) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    FilmPoster(state.movieDetails.poster)
                }
                item {
                    FilmShortDetails(state.movieDetails.reviews, state.movieDetails.name ?: "")
                }
                item {
                    FilmDescription(state.movieDetails.description)
                }
                item {
                    FilmGenres(state.movieDetails.genres)
                }
                item {
                    FilmAbout(state.movieDetails)
                }
                item {
                    FilmReviews(state.movieDetails.reviews)
                }
            }
        }
    }
}

@Composable
fun TopBar(
    onNavigationRequested: (navigationEffect: FilmScreenContract.Effect.Navigation) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth().height(44.dp)) {
        var isClickable by remember { mutableStateOf(true) }

        Icon(
            painter = painterResource(R.drawable.back_icon),
            contentDescription = null,
            modifier = Modifier.padding(start = 15.dp).height(12.dp).width(12.dp).align(Alignment.CenterStart)
                .clickable {
                    if (isClickable) {
                        isClickable = false
                        onNavigationRequested(FilmScreenContract.Effect.Navigation.Back)
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000L)
                            isClickable = true
                        }
                    }
                }
        )
    }
}

@Composable
fun FilmPoster(
    poster: String?
) {
    Box(modifier = Modifier.fillMaxWidth().height(497.dp)) {
        AsyncImage(
            model = poster,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(497.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        0.4f to Color.Transparent,
                        0.95f to MaterialTheme.colorScheme.background
                    )
                )
        )
    }
}

@Composable
fun FilmShortDetails(
    movieReviews: List<ReviewModel>?,
    name: String
) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp)
    ) {
        val filmRating: FilmRating? = calculateFilmRating(movieReviews)
        if(filmRating != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .height(20.dp)
                    .width(37.dp)
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
                )
            }
        }
        Text(
            text = name,
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W700,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.align(Alignment.Center).fillMaxWidth().padding(horizontal = 60.dp)
        )

        Box(
            modifier = Modifier.height(40.dp).width(40.dp)
                .clip(CircleShape).align(Alignment.CenterEnd)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Icon(
                painter = painterResource(R.drawable.heart_disabled),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

private fun calculateFilmRating(reviews: List<ReviewModel>?): FilmRating? {
    if(reviews == null) {
        return null
    } else {

        var sumScore: Int = 0
        reviews.forEach {
            sumScore += it.rating
        }

        val rating = (sumScore.toDouble() / reviews.count())

        val color = when {
            rating >= 1.0 && rating < 2.0 -> {
                Color(0xFFE64646)
            }
            rating >= 2.0 && rating < 3.0 -> {
                Color(0xFFE64646)
            }
            rating >= 3.0 && rating < 4.0 -> {
                Color(0xFFF05C44)
            }
            rating >= 4.0 && rating < 5.0 -> {
                Color(0xFFFFA000)
            }
            rating >= 5.0 && rating < 6.0 -> {
                Color(0xFFFFD54F)
            }
            rating >= 6.0 && rating < 7.0 -> {
                Color(0xFFFFD54F)
            }
            rating >= 7.0 && rating < 8.0 -> {
                Color(0xFFA3CD4A)
            }
            rating >= 8.0 && rating < 9.0 -> {
                Color(0xFFA3CD4A)
            }
            else -> {
                Color(0xFF4BB34B)
            }
        }

        return FilmRating(
            rating = if (rating != 10.0)
            { rating.toString().substring(startIndex = 0, endIndex = 3) }
            else { rating.toString().substring(startIndex = 0, endIndex = 4) },
            color = color
        )
    }
}

@Composable
fun FilmDescription(
    movieDescription: String?
) {
    if (movieDescription != null) {
        var expanded by remember { mutableStateOf(false) }
        var boxHeight by remember { mutableStateOf(0.dp) }
        val localDensity = LocalDensity.current
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        if (!expanded) {
                            boxHeight = with(localDensity) { coordinates.size.height.toDp() }
                        }
                    }
            ) {
                Text(
                    text = movieDescription,
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = if (expanded) Int.MAX_VALUE else 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, end = 15.dp, start = 15.dp)
                        .clickable { expanded = !expanded }
                )

                if (!expanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(boxHeight)
                            .background(
                                brush = Brush.verticalGradient(
                                    0f to Color.Transparent,
                                    0.9f to MaterialTheme.colorScheme.background
                                )
                            )
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp)
                    .clickable { expanded = !expanded },
                horizontalArrangement = spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) {
                        "Скрыть"
                    } else {
                        "Подробнее"
                    },
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    maxLines = if (expanded) Int.MAX_VALUE else 3,
                )
                if (!expanded) {
                    Icon(
                        painterResource(R.drawable.arrow_down),
                        null,
                        modifier = Modifier.width(10.dp).height(10.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilmGenres(
    movieGenres: List<GenreModel>?
) {
    if(movieGenres != null) {
        Column(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 20.dp),
            verticalArrangement = spacedBy(10.dp)
        ) {
            Text(
                text = "Жанры",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W700,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
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

@Composable
fun FilmAbout(
    movieDetails: MovieDetailsModel
) {
    Column(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 25.dp),
        verticalArrangement = spacedBy(10.dp)
    ) {
        Text(
            text = "О фильме",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W700,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        )

        Row(horizontalArrangement = spacedBy(5.dp)) {
            Text(
                text = "Год",
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
                text = movieDetails.year.toString(),
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

        Row(horizontalArrangement = spacedBy(5.dp)) {
            Text(
                text = "Страна",
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
                text = movieDetails.country ?: "-",
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

        Row(horizontalArrangement = spacedBy(5.dp)) {
            Text(
                text = "Слоган",
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
                if(movieDetails.tagline != null && movieDetails.tagline != "-") {
                    "\"" + movieDetails.tagline + "\""
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

        Row(horizontalArrangement = spacedBy(5.dp)) {
            Text(
                text = "Режиссёр",
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
                text = movieDetails.director ?: "",
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

        Row(horizontalArrangement = spacedBy(5.dp)) {
            Text(
                text = "Бюджет",
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
                if(movieDetails.budget != null) {
                    "$" + movieDetails.budget.toString()
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

        Row(horizontalArrangement = spacedBy(5.dp)) {
            Text(
                text = "Сборы в мире",
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
                if(movieDetails.fees != null) {
                    "$" + movieDetails.fees.toString()
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

        Row(horizontalArrangement = spacedBy(5.dp)) {
            Text(
                text = "Возраст",
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
                text = movieDetails.ageLimit.toString() + "+",
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

        Row(horizontalArrangement = spacedBy(5.dp)) {
            Text(
                text = "Время",
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
                text = movieDetails.time.toString() + " мин.",
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
}

@Composable
fun FilmReviews(
    reviews: List<ReviewModel>?
) {
    var showDialog by remember { mutableStateOf(false) }

    MyDialog(showDialog, onDismiss = { showDialog = !showDialog })

    Column(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 25.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)) {
            Text(
                text = "Отзывы",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W700,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            )

            if(true) {
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape).align(Alignment.CenterEnd)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { showDialog = true }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        reviews?.forEach {
            Column(
                verticalArrangement = spacedBy(8.dp)
            ){
                if(!it.isAnonymous) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Row(
                            horizontalArrangement = spacedBy(10.dp)
                        ) {
                            AsyncImage(
                                model = it.author.avatar ?: "",
                                contentDescription = null,
                                modifier = Modifier.size(40.dp).clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                verticalArrangement = spacedBy(2.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = it.author.nickName ?: "",
                                    style = TextStyle(
                                        fontFamily = interFamily,
                                        fontWeight = FontWeight.W500,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Start
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(
                                    text = "мой отзыв",
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

                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            horizontalArrangement = spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(26.dp)
                                    .clip(RoundedCornerShape(35.dp))
                                    .background(Color.Green)
                            ) {
                                Row(
                                    horizontalArrangement = spacedBy(4.dp),
                                    modifier = Modifier.padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painterResource(R.drawable.small_star),
                                        null
                                    )
                                    Text(
                                        text = "9",
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

                            var expanded by remember { mutableStateOf(false) }
                            Menu( expanded = expanded, onDismiss = { expanded = !expanded }, onEditRequested = { showDialog = !showDialog })

                            Box(
                                modifier = Modifier
                                    .height(26.dp).width(26.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onSurface)
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
                        }
                    }
                } else if (it.isAnonymous) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Row(
                            horizontalArrangement = spacedBy(10.dp)
                        ) {
                            AsyncImage(
                                model = R.drawable.logo,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp).clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                verticalArrangement = spacedBy(2.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Аноним",
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

                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            horizontalArrangement = spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(26.dp)
                                    .clip(RoundedCornerShape(35.dp))
                                    .background(Color.Green)
                            ) {
                                Row(
                                    horizontalArrangement = spacedBy(4.dp),
                                    modifier = Modifier.padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painterResource(R.drawable.small_star),
                                        null
                                    )
                                    Text(
                                        text = "9",
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
                        }
                    }
                }
                Text(
                    text = it.reviewText ?: "",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start
                    )
                )
                Text(
                    text = formatDateFromApi(it.createDateTime),
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
        }
    }
}

@Composable
fun Menu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onEditRequested: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        offset = DpOffset(0.dp, 5.dp),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = "Редактировать",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
            },
            onClick = {
                onDismiss()
                onEditRequested()
                      },
            trailingIcon = {
                Icon(
                    painterResource(R.drawable.ic_pen),
                    null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        )

        DropdownMenuItem(
            text = {
                Text(
                    text = "Удалить",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        color =  MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
            },
            onClick = { },
            trailingIcon = {
                Icon(
                    painterResource(R.drawable.ic_trash_can),
                    null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}

@Composable
fun MyDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {

    var checked by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = spacedBy(10.dp)
                ) {
                    Text(
                        text = "Оставить отзыв",
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W700,
                            fontSize = 20.sp,
                            color =  MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        ),
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row() {
                        Text(
                            text = "Тут звёздочки",
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.W700,
                                fontSize = 16.sp,
                                color =  MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = "",
                        colors = OutlinedTextFieldDefaults.colors(
                        ),
                        textStyle = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W400,
                            fontSize = 15.sp
                        ),
                        onValueChange = {

                        },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.clickable {
                            checked = !checked
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = checked,
                            onCheckedChange = null
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Анонимный отзыв",
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.W500,
                                fontSize = 15.sp,
                                color =  MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                    ) {
                        Text(
                            text = "Сохранить",
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.W600,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            ),
                        )
                    }

                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                    ) {
                        Text(
                            text = "Отмена",
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.W600,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            ),
                        )
                    }
                }
            }
        }
    }
}

private fun formatDateFromApi(date: String): String {
    val parts = date.split("-")
    val year = parts[0]
    val month = parts[1]
    val day = parts[2]
    return "${day.substring(startIndex = 0, endIndex = 2)}.$month.$year"
}