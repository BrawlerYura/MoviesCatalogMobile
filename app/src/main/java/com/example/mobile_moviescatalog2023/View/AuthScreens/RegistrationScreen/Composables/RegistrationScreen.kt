package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.Composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.Composables.LoginHeader
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.Composables.BottomRegistrationText
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LoginHeader { onNavigationRequested(RegistrationContract.Effect.Navigation.Back) }

                Text(
                    text = stringResource(R.string.registration_button),
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                NameTextBox(state, onEventSent)

                GenderTextBox(state, onEventSent)

                LoginTextBox(state, onEventSent)

                MailTextBox(state, onEventSent)

                BirthDateTextBox(state, onEventSent)

                ContinueButton(state) {
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
                }
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