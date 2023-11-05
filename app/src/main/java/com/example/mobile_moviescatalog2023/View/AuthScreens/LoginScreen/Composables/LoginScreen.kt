package com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.Composables

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen.SplashContract
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreen(
    state: LoginContract.State,
    onEventSent: (event: LoginContract.Event) -> Unit,
    effectFlow: Flow<LoginContract.Effect>?,
    onNavigationRequested: (navigationEffect: LoginContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is LoginContract.Effect.Navigation.ToMain -> onNavigationRequested(effect)
                is LoginContract.Effect.Navigation.ToRegistration -> onNavigationRequested(effect)
                is LoginContract.Effect.Navigation.Back -> onNavigationRequested(effect)
            }
        }?.collect()
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

                LoginHeader { onEventSent(LoginContract.Event.NavigationBack) }

                Text(
                    text = stringResource(R.string.login_title),
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                LoginTextBox(state, onEventSent)

                PasswordTextBox(state, onEventSent)

                SignInButton(state) { haptic -> onEventSent(LoginContract.Event.SignIn(haptic)) }
            }
            LoginBottomText { onEventSent(LoginContract.Event.NavigationToRegistration) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        state = loginStatePreview,
        onEventSent = { },
        onNavigationRequested = { },
        effectFlow = null
    )
}

val loginStatePreview = LoginContract.State (
    login = "my login",
    password = "password",
    isSuccess = null,
    buttonEnabled = true,
    errorMessage = null
)