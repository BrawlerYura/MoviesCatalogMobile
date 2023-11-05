package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract

@Composable
fun BottomNavigationBar(
    onNavigationToMainRequested: () -> Unit,
    onNavigationToFavoriteRequested: () -> Unit,
    onNavigationToProfileRequested: () -> Unit,
    currentScreen: Int
) {
    val screens = listOf(
            "home",
            "favorite",
            "profile"
    )
    Box(
        modifier = Modifier
            .background(Color(0xFF545458).copy(0.65f))
    ) {
        NavigationBar(
            containerColor = Color(0xFF161616),
            modifier = Modifier.padding(top = 1.dp)
        ) {
            screens.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        when (index) {
                            0 -> {
                                if(currentScreen != 0){
                                    onNavigationToMainRequested()
                                }
                            }

                            1 -> {
                                if(currentScreen != 1) {
                                    onNavigationToFavoriteRequested()
                                }
                            }

                            else -> {
                                if(currentScreen != 2) {
                                    onNavigationToProfileRequested()
                                }
                            }
                        }
                    },
                    icon = {
                        when(index) {
                            0 -> {
                                Icon(
                                    painter = painterResource(R.drawable.ic_home_enabled),
                                    contentDescription = item,
                                    tint =
                                    if(index == currentScreen) { Color(0xFFFC315E) }
                                    else { Color(0xFF909499) }
                                )
                            }
                            1 -> {
                                Icon(
                                    painter = painterResource(R.drawable.ic_favorite_enabled),
                                    contentDescription = item,
                                    tint =
                                    if(index == currentScreen) { Color(0xFFFC315E) }
                                    else { Color(0xFF909499) }
                                )
                            }
                            else -> {
                                Icon(
                                    painter = painterResource(R.drawable.ic_profile_enabled),
                                    contentDescription = item,
                                    tint =
                                    if(index == currentScreen) { Color(0xFFFC315E) }
                                    else { Color(0xFF909499) }
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavigationBarPreview() {
    BottomNavigationBar(
        onNavigationToMainRequested = { },
        onNavigationToFavoriteRequested = { },
        onNavigationToProfileRequested = { },
        currentScreen = 1
    )
}