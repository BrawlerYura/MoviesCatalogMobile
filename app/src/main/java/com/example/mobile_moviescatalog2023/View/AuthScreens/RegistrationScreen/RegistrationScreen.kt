package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.Composables.LoginHeader
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.Composables.BottomRegistrationText
import com.example.mobile_moviescatalog2023.View.Common.ChooseGenderBox
import com.example.mobile_moviescatalog2023.View.Common.MyBirthDateTextBox
import com.example.mobile_moviescatalog2023.View.Common.MyButton
import com.example.mobile_moviescatalog2023.View.Common.MyTextFieldBox
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun RegistrationScreen(
    state: RegistrationContract.State,
    onEventSent: (event: RegistrationContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: RegistrationContract.Effect.Navigation) -> Unit
) {
    FilmusTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.95f)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = spacedBy(15.dp)
            ) {

                LoginHeader { onNavigationRequested(RegistrationContract.Effect.Navigation.Back) }

                Text(
                    text = stringResource(R.string.registration_button),
                    style = MyTypography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                MyTextFieldBox(
                    value = state.name,
                    isError = false,
                    isValid = state.isNameValid == false,
                    onSaveEvent = { text ->
                        onEventSent(RegistrationContract.Event.SaveNameEvent(text))
                    },
                    headerText = stringResource(R.string.name_label),
                    errorText = stringResource(R.string.invalid_name_message)
                )

                ChooseGenderBox(
                    currentIndex = state.gender,
                    onSaveEvent = { gender ->
                        onEventSent(RegistrationContract.Event.SaveGenderEvent(gender))
                    }
                )

                MyTextFieldBox(
                    value = state.login,
                    isError = false,
                    isValid = state.isLoginValid == false,
                    onSaveEvent = { text ->
                        onEventSent(RegistrationContract.Event.SaveLoginEvent(text))
                    },
                    headerText = stringResource(R.string.login_label),
                    errorText = stringResource(R.string.invalid_login_message)
                )

                MyTextFieldBox(
                    value = state.email,
                    isError = false,
                    isValid = state.isEmailValid == false,
                    onSaveEvent = { text ->
                        onEventSent(RegistrationContract.Event.SaveEmailEvent(text))
                    },
                    headerText = stringResource(R.string.email_label),
                    errorText = stringResource(R.string.invalid_email_message)
                )

                MyBirthDateTextBox(
                    value = state.birthDate,
                    isValid = state.isBirthDateValid == false,
                    isError = false,
                    onSaveDateEvent = { date ->
                        onEventSent(
                            RegistrationContract.Event.SaveBirthDateWithFormatEvent(
                                date
                            )
                        )
                    },
                    onSaveTextEvent = { text ->
                        onEventSent(
                            RegistrationContract.Event.SaveBirthDateEvent(text)
                        )
                    }
                )

                MyButton(
                    isEnabled = state.isBirthDateValid == true &&
                        state.isLoginValid == true &&
                        state.isEmailValid == true &&
                        state.isNameValid == true,
                    onEventSent = {
                        onNavigationRequested(
                            RegistrationContract.Effect.Navigation.NextScreen(
                                registerRequestBody = RegisterRequestBody(
                                    userName = state.login,
                                    name = state.name,
                                    password = "",
                                    email = state.email,
                                    birthDate = state.apiBirthDate,
                                    gender = state.gender
                                )
                            )
                        )
                    },
                    text = stringResource(R.string.continue_button),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
            BottomRegistrationText {
                onNavigationRequested(RegistrationContract.Effect.Navigation.ToLogin)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen(
        state = registrationStatePreview,
        onEventSent = { },
        onNavigationRequested = { }
    )
}

val registrationStatePreview = RegistrationContract.State(
    login = "Shomas Thelby",
    name = "Cherry Tomatoes",
    email = "sis.sas@gmail.com",
    birthDate = "01.01.2001",
    gender = 0,
    apiBirthDate = "",
    isNameValid = false,
    isLoginValid = false,
    isEmailValid = false,
    isBirthDateValid = false
)