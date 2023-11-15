package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun FilmPoster(
    poster: String?,
    scroll: LazyListState
) {
    val posterHeight = 497.dp
    val posterHeightPx = with(LocalDensity.current) { posterHeight.toPx() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(posterHeight)
            .graphicsLayer {
                translationY = scroll.firstVisibleItemScrollOffset / 3f
                alpha = (-1f / posterHeightPx) * scroll.firstVisibleItemScrollOffset + 1
            }
    ) {
        AsyncImage(
            model = poster,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(497.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        0.4f to Color.Transparent,
                        0.95f to MaterialTheme.colorScheme.background
                    )
                )
        )
    }
}