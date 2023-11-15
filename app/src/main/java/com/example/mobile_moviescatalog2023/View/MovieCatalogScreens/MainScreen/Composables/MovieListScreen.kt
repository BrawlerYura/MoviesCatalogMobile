package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.mainStatePreview
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@Composable
fun MovieListScreen(
    state: MainScreenContract.State,
    lazyListState: LazyListState,
    onUpdateListRequested: () -> Unit,
    onNavigationRequested: (id: String) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Carousel(state.movieList.take(4), onNavigationRequested)
            }
            item {
                Text(
                    text = stringResource(R.string.catalog),
                    style = MyTypography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(
                        bottom = 15.dp,
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                )
            }
            itemsIndexed(state.movieList) { index, item ->
                if (index >= 4) {
                    FilmCard(item, index, state, onNavigationRequested)
                }
            }
        }
        if(state.isUpdatingList) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).size(38.dp).padding(4.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }

    if (
        !lazyListState.canScrollForward &&
        (state.currentMoviePage - 1 < state.pageCount) &&
        !state.isUpdatingList
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
        onNavigationRequested = { },
        lazyListState = LazyListState()
    )
}