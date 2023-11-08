package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.View.Common.ChooseGenderBox
import com.example.mobile_moviescatalog2023.View.Common.MyBirthDateTextBox
import com.example.mobile_moviescatalog2023.View.Common.MyButton
import com.example.mobile_moviescatalog2023.View.Common.MyTextFieldBox
import com.example.mobile_moviescatalog2023.View.Common.BottomNavigationBar
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = spacedBy(15.dp)
                ) {
                    MyTextFieldBox(
                        value = state.email,
                        isError = state.isSuccess == false,
                        isValid = state.isEmailValid == false,
                        onSaveEvent = { text ->
                            onEventSent(ProfileScreenContract.Event.SaveEmailEvent(text))
                        },
                        headerText = stringResource(R.string.email_label),
                        errorText = stringResource(R.string.invalid_email_message)
                    )

                    MyTextFieldBox(
                        value = state.userIconUrl ?: "",
                        isError = state.isSuccess == false,
                        isValid = false,
                        onSaveEvent = { text ->
                            onEventSent(ProfileScreenContract.Event.SaveUserIconUrl(text))
                        },
                        headerText = stringResource(R.string.url_to_profile_icon),
                        errorText = ""
                    )

                    MyTextFieldBox(
                        value = state.name,
                        isError = state.isSuccess == false,
                        isValid = state.isNameValid == false,
                        onSaveEvent = { text ->
                            onEventSent(ProfileScreenContract.Event.SaveNameEvent(text))
                        },
                        headerText = stringResource(R.string.name_label),
                        errorText = stringResource(R.string.invalid_name_message)
                    )

                    ChooseGenderBox(
                        currentIndex = state.gender,
                        onSaveEvent = { gender ->
                            onEventSent(ProfileScreenContract.Event.SaveGenderEvent(gender))
                        }
                    )

                    MyBirthDateTextBox(
                        value = state.birthDate,
                        isValid = state.isBirthDateValid == false,
                        isError = state.isSuccess == false,
                        onSaveDateEvent = { date ->
                            onEventSent(
                                ProfileScreenContract.Event.SaveBirthDateWithFormatEvent(
                                    date
                                )
                            )
                        },
                        onSaveTextEvent = { text ->
                            onEventSent(
                                ProfileScreenContract.Event.SaveBirthDateEvent(text)
                            )
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = spacedBy(15.dp)
                ) {
                    val haptic = LocalHapticFeedback.current
                    MyButton(
                        isEnabled = state.isEnable,
                        onEventSent = {
                            onEventSent(
                                ProfileScreenContract.Event.PutNewUserDetails(
                                    haptic
                                )
                            )
                        },
                        text = stringResource(R.string.save),
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )

                    MyButton(
                        isEnabled = state.isCancelEnable,
                        onEventSent = { onEventSent(ProfileScreenContract.Event.LoadUserDetails) },
                        text = stringResource(R.string.refuse),
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                }

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
    isEnable = true,
    isCancelEnable = true,
    isNameValid = true,
    isEmailValid = true,
    isBirthDateValid = true
)