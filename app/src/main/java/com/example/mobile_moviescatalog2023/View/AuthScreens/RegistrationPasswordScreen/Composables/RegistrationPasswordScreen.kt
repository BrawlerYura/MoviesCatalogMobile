package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.Composables.LoginHeader
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.Composables.BottomRegistrationText
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.Composables.PasswordTextBox
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.Composables.RepeatPasswordTextBox
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.View.Common.MyButton
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun RegistrationPasswordScreen(
    state: RegistrationPasswordContract.State,
    onEventSent: (event: RegistrationPasswordContract.Event) -> Unit,
    effectFlow: Flow<RegistrationPasswordContract.Effect>?,
    onNavigationRequested: (navigation: RegistrationPasswordContract.Effect.Navigation) -> Unit,
    registerRequestBody: RegisterRequestBody
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is RegistrationPasswordContract.Effect.Navigation.ToMain -> onNavigationRequested(effect)
                is RegistrationPasswordContract.Effect.Navigation.ToLogin -> onNavigationRequested(effect)
                is RegistrationPasswordContract.Effect.Navigation.Back -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    if(!state.isBodyLoaded) {
        onEventSent(RegistrationPasswordContract.Event.LoadRegisterRequestBody(registerRequestBody))
    }

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

                LoginHeader { onEventSent(RegistrationPasswordContract.Event.NavigationBack) }

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

                PasswordTextBox(state, onEventSent)

                RepeatPasswordTextBox(state, onEventSent)

                val haptic = LocalHapticFeedback.current
                MyButton(
                    isEnabled = state.isPasswordValid == true && state.isRepPasswordValid == true,
                    onEventSent = { onEventSent(RegistrationPasswordContract.Event.SignUp(haptic)) },
                    text = stringResource(R.string.finish_registration_button),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                    )
            }
            BottomRegistrationText { onEventSent(RegistrationPasswordContract.Event.NavigationToLogin) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationPasswordScreenPreview() {
    RegistrationPasswordScreen(
        state = repeatPasswordStatePreview,
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { },
        registerRequestBody = RegisterRequestBody(
            userName = "",
            name = "",
            password = "",
            email = "",
            birthDate = "",
            gender = 0
        )
    )
}

val repeatPasswordStatePreview = RegistrationPasswordContract.State (
    password = "password",
    repPassword = "password",
    userName = "Shomas Thelby",
    name = "Cherry Tomatoes",
    email = "sis.sas@gmail.com",
    birthDate = "01.01.2001",
    gender = 0,
    isSuccess = true,
    isPasswordValid = true,
    isRepPasswordValid = true,
    errorMessage = null,
    isBodyLoaded = true
)