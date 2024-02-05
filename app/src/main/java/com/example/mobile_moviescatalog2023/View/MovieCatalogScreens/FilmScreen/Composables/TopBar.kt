package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.filmScreensPreviewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TopBar(
    onNavigationRequested: () -> Unit,
    showName: Boolean,
    state: FilmScreenContract.State,
    onEventSent: (FilmScreenContract.Event) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .padding(horizontal = 16.dp)) {
        var isClickable by remember { mutableStateOf(true) }

        IconButton(
            onClick = {
                if (isClickable) {
                    isClickable = false
                    onNavigationRequested()
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000L)
                        isClickable = true
                    }
                }
            },
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterStart),
            content = {
                Icon(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .height(12.dp)
                        .width(12.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        if (showName) {
            Text(
                text = state.movieDetails.name ?: "",
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W700,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp)
            )

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterEnd)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        if (state.isMyFavorite) {
                            onEventSent(FilmScreenContract.Event.DeleteFavorite(state.movieDetails.id))
                        } else {
                            onEventSent(FilmScreenContract.Event.AddToFavorite(state.movieDetails.id))
                        }
                    },
            ) {
                Icon(
                    painter = if (state.isMyFavorite) {
                        painterResource(R.drawable.heart_enabled)
                    } else {
                        painterResource(R.drawable.heart_disabled)
                    },
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center),
                    tint = if (state.isMyFavorite) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    TopBar(
        onNavigationRequested = { },
        showName = true,
        state = filmScreensPreviewState
    ) { }
}