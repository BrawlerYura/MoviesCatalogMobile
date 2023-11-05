package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
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
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun ProfileScreen(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit,
    effectFlow: Flow<ProfileScreenContract.Effect>?,
    onNavigationRequested: (navigationEffect: ProfileScreenContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is ProfileScreenContract.Effect.Navigation.ToMain -> onNavigationRequested(effect)
                is ProfileScreenContract.Effect.Navigation.ToFavorite -> onNavigationRequested(effect)
                is ProfileScreenContract.Effect.Navigation.ToIntroducing -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    FilmusTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onNavigationToMainRequested = { onEventSent(ProfileScreenContract.Event.NavigationToMain) },
                    onNavigationToProfileRequested = {  },
                    onNavigationToFavoriteRequested = { onEventSent(ProfileScreenContract.Event.NavigationToFavorite) },
                    currentScreen = 2
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = spacedBy(15.dp)
            ) {
                ProfileBox(state)
                MailTextBox(state, onEventSent)
                ProfileIconUrlBox(state, onEventSent)
                NameTextBox(state, onEventSent)
                GenderTextBox(state, onEventSent)
                BirthDateTextBox(state, onEventSent)
                SaveUserDetailsButton(state, onEventSent)
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
                        .padding(top = 5.dp, bottom = 5.dp)
                        .clickable {
                            onEventSent(ProfileScreenContract.Event.Logout)
                        }
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        state = profileStatePreview,
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { }
    )
}

val profileStatePreview = ProfileScreenContract.State(
    id = "",
    nickName = "BrawlerYura",
    email = "my@email.com",
    userIconUrl = null,
    name = "my name",
    gender = 0,
    birthDate = "30.07.2004",
    isSuccess = null,
    errorMessage = null,
    profileModel = null,
    isEnable = true
)