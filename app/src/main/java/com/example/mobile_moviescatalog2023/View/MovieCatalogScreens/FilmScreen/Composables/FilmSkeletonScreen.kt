package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.View.Common.shimmerEffect
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract

@Composable
fun FilmSkeletonScreen(
    state: FilmScreenContract.State,
    onEventSent: (event: FilmScreenContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                { onEventSent(FilmScreenContract.Event.NavigationBack) },
                false,
                state,
                onEventSent
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Absolute.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(497.dp)
                .shimmerEffect())

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(51.dp)
                        .height(26.dp)
                        .align(Alignment.CenterStart)
                        .clip(RoundedCornerShape(5.dp))
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(30.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(5.dp))
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .align(Alignment.CenterEnd)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Absolute.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilmSkeletonScreenPreview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        FilmSkeletonScreen(
            state = filmScreensPreviewState,
            onEventSent = { }
        )
    }
}