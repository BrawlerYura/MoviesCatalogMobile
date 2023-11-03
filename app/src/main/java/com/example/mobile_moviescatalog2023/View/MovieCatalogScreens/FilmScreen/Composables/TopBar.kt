package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    TopBar(
        onNavigationRequested = { }
    )
}