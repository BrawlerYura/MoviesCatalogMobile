package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun MovieListScreen(
    state: MainScreenContract.State,
    onUpdateListRequested: () -> Unit,
    onNavigationRequested: (id: String) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Carousel(state, onNavigationRequested)
        }
        item {
            Text(
                text = stringResource(R.string.catalog),
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(
                    bottom = 15.dp,
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            )
        }
        itemsIndexed(state.movieList) { index, item ->
            if(state.filmRatingsList.isNotEmpty()) {
                FilmCard(item, state.filmRatingsList[index], onNavigationRequested)
            }
        }
    }

    if (
        !lazyListState.canScrollForward &&
        (state.currentMoviePage - 1 < state.pageCount)
    ) {
        onUpdateListRequested()
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieListScreenPreview() {
    MovieListScreen(
        state = mainStatePreview,
        onUpdateListRequested = { },
        onNavigationRequested = { }
    )
}