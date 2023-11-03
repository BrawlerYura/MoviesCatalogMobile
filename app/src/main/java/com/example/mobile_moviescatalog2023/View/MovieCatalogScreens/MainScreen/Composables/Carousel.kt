package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(
    state: MainScreenContract.State,
    onNavigationRequested: (navigationEffect: MainScreenContract.Effect.Navigation) -> Unit
) {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { state.movieCarouselList.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % pagerState.pageCount,
                animationSpec = tween(500)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(497.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) { page ->
            AsyncImage(
                model = state.movieCarouselList[page].poster,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
                    .clickable {
                        onNavigationRequested(MainScreenContract.Effect.Navigation.ToFilm(state.movieCarouselList[page].id))
                    },
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(color = Color.Black.copy(alpha = 0.25f))
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(state.movieCarouselList.size) { pageIndex ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                    ){
                        if(pagerState.currentPage == pageIndex) {
                            Icon(painterResource(R.drawable.dotactive), contentDescription = null)
                        } else {
                            Icon(painterResource(R.drawable.dotcircle), contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}