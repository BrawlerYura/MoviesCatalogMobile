package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun ProfileScreen(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: ProfileScreenContract.Effect.Navigation) -> Unit,
    onBottomNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(true) {
        onEventSent(ProfileScreenContract.Event.LoadUserDetails)
    }

    FilmusTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onBottomNavigationRequested,
                    2
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    ProfileBox(state)
                    MailTextBox(state, onEventSent)
                    ProfileIconUrlBox(state, onEventSent)
                    NameTextBox(state, onEventSent)
                    GenderTextBox(state, onEventSent)
                    BirthDateTextBox(state, onEventSent)
                    SaveUserDetailsButton(onEventSent)
                    CancellButton(onEventSent)

                    Text(
                        text = stringResource(R.string.logout),
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            .padding(top = 24.dp, bottom = 10.dp)
                            .clickable {
                                onEventSent(ProfileScreenContract.Event.Logout)
                                onNavigationRequested(ProfileScreenContract.Effect.Navigation.ToIntroducing)
                            }
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        state = ProfileScreenContract.State(
            id = "",
            nickName = "BrawlerYura",
            email = "my@email.com",
            userIconUrl = null,
            name = "my name",
            gender = 0,
            birthDate = "30.07.2004",
            isSuccess = null
        ),
        onEventSent = { },
        onNavigationRequested = { },
        onBottomNavigationRequested = { }
    )
}