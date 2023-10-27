package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun BottomNavigationBar(
    onNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
) {
    val screens = listOf(
        BottomNavItem("Главная", Icons.Default.Home, "home"),
        BottomNavItem("Любимое", Icons.Default.Favorite, "favorite"),
        BottomNavItem("Профиль", Icons.Default.Person, "profile")
    )
    Box(
        modifier = Modifier
            .height(68.dp)
            .background(Color(0xFF545458).copy(0.65f))
    ) {
        NavigationBar(containerColor = Color(0xFF161616), modifier = Modifier.padding(top = 1.dp)) {
            screens.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        when (index) {
                            0 -> {
                                onNavigationRequested(MovieNavigationContract.Effect.Navigation.ToMain)
                            }

                            2 -> {
                                onNavigationRequested(MovieNavigationContract.Effect.Navigation.ToFavorite)
                            }

                            else -> {
                                onNavigationRequested(MovieNavigationContract.Effect.Navigation.ToProfile)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.route,
                            tint = Color(0xFF909499)
                        )
                    },
                    label = {
                        Text(
                            item.label,
                            style = TextStyle(
                                fontFamily = interFamily,
                                fontWeight = FontWeight.W400,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            ),
                            color = Color(0xFF909499)
                        )
                    },
                )
            }
        }
    }
}


data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
)