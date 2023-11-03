package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.R

@Composable
fun FilmPoster(
    poster: String?
) {
    Box(modifier = Modifier.fillMaxWidth().height(497.dp)) {
        AsyncImage(
            model = poster ?: R.drawable.logo,
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