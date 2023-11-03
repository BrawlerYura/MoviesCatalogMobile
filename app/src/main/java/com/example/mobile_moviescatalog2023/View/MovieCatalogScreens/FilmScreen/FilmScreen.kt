package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.GenreModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewModifyModel
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.R
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
                    FilmShortDetails(
                        state.movieDetails.reviews,
                        state.movieDetails.name ?: "",
                        state,
                        onEventSent,
                        filmId
                    )
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
                    FilmReviews(state.movieDetails.reviews, state, onEventSent, filmId)
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
    name: String,
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    id: String
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
                .clickable {
                    if(state.isMyFavorite) {
                        onEventSent(FilmScreenContract.Event.DeleteFavorite(id))
                    } else {
                        onEventSent(FilmScreenContract.Event.AddToFavorite(id))
                    }
                },
        ) {
            Icon(
                painter = if(state.isMyFavorite) {
                    painterResource(R.drawable.heart_enabled)
                } else {
                    painterResource(R.drawable.heart_disabled)
                },
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
                tint = if(state.isMyFavorite) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
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
            rating >= 0.0 && rating < 3.0 -> {
                Color(0xFFE64646)
            }
            rating >= 3.0 && rating < 4.0 -> {
                Color(0xFFF05C44)
            }
            rating >= 4.0 && rating < 5.0 -> {
                Color(0xFFFFA000)
            }
            rating >= 5.0 && rating < 7.0 -> {
                Color(0xFFFFD54F)
            }
            rating >= 7.0 && rating < 9.0 -> {
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
    if (movieDescription != null && movieDescription != "-") {
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
                    maxLines = if (expanded) Int.MAX_VALUE else 2,
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
    reviews: List<ReviewModel>?,
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit,
    filmId: String
) {
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

            if(!state.isWithMyReview) {
                var showDialog by remember { mutableStateOf(false) }

                ReviewDialog(
                    showDialog,
                    onDismiss = { showDialog = !showDialog },
                    state,
                    onEventSent,
                    filmId
                )
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
            ) {
                val isMyReview = remember {
                    mutableStateOf(
                        try { it.author.userId == Network.userId }
                        catch (e: Exception) {
                            false
                        }
                    )
                }
                if(!it.isAnonymous || isMyReview.value ) {
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
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Column(
                                    verticalArrangement = spacedBy(2.dp),
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier.align(Alignment.TopStart).padding(end = 100.dp)
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
                                    if (it.author.userId == Network.userId) {
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
                                Box(
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(
                                                end = if (it.author.userId == Network.userId) {
                                                    36.dp
                                                } else {
                                                    0.dp
                                                }
                                            )
                                            .height(26.dp)
                                            .clip(RoundedCornerShape(35.dp))
                                            .background(calculateRatingColor(it.rating))
                                            .align(Alignment.TopEnd)
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
                                                text = it.rating.toString(),
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
                                    if (isMyReview.value) {
                                        var showDialog by remember { mutableStateOf(false) }

                                        ReviewDialog(
                                            showDialog,
                                            onDismiss = { showDialog = !showDialog },
                                            state,
                                            onEventSent,
                                            filmId,
                                            reviewId = it.id,
                                            rating = it.rating,
                                            reviewText = it.reviewText,
                                            isAnonymous = it.isAnonymous
                                            )

                                        var expanded by remember { mutableStateOf(false) }
                                        Menu(
                                            expanded = expanded,
                                            onDismiss = { expanded = !expanded },
                                            onEditRequested = { showDialog = !showDialog },
                                            onDeleteRequested = {
                                                onEventSent(FilmScreenContract.Event.DeleteMyReview(filmId, it.id))
                                            }
                                        )

                                        Box(
                                            modifier = Modifier
                                                .height(26.dp).width(26.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.onSurface)
                                                .align(Alignment.TopEnd)
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
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Row(
                            horizontalArrangement = spacedBy(10.dp),
                            modifier = Modifier.align(Alignment.TopStart).padding(end = 60.dp)
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

                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .clip(RoundedCornerShape(35.dp))
                                .background(calculateRatingColor(it.rating))
                                .align(Alignment.TopEnd)
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
                                    text = it.rating.toString(),
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

private fun calculateRatingColor(rating: Int): Color {
    return when(rating) {
        in 0 until 3 -> {
            Color(0xFFE64646)
        }
        in 3 until 4 -> {
            Color(0xFFF05C44)
        }
        in 4 until 6 -> {
            Color(0xFFFFA000)
        }
        in 6 until 8 -> {
            Color(0xFFFFD54F)
        }
        in 8 until 9 -> {
            Color(0xFFA3CD4A)
        }
        else -> {
            Color(0xFF4BB34B)
        }
    }
}

@Composable
fun Menu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onEditRequested: () -> Unit,
    onDeleteRequested: () -> Unit
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
        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(Color.LightGray))
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
            onClick = {
                onDismiss()
                onDeleteRequested()
            },
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
    Log.e("a", isEditing.toString())
    LaunchedEffect(isEditing) {
//        if (isEditing) {
            onEventSent(FilmScreenContract.Event.SaveReviewRating(rating ?: 1))
            onEventSent(FilmScreenContract.Event.SaveReviewText(reviewText ?: ""))
            onEventSent(FilmScreenContract.Event.SaveIsAnonymous(isAnonymous ?: false))
//        }
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
                    OutlinedTextField(
                        value = state.myReviewTextField,
                        colors = OutlinedTextFieldDefaults.colors(
                        ),
                        textStyle = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W400,
                            fontSize = 15.sp
                        ),
                        onValueChange = {
                            onEventSent(FilmScreenContract.Event.SaveReviewText(it))
                        },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier.fillMaxWidth().height(100.dp)
                    )
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
                    Spacer(modifier = Modifier.height(10.dp))
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