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
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun LoginScreen(
    state: LoginContract.State,
    onEventSent: (event: LoginContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: LoginContract.Effect.Navigation) -> Unit
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

                LoginHeader { onNavigationRequested(LoginContract.Effect.Navigation.Back) }

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
            LoginBottomText { onNavigationRequested(LoginContract.Effect.Navigation.ToRegistration) }
        }

        if(state.isSuccess == true) {
            onNavigationRequested(LoginContract.Effect.Navigation.ToMain)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        state = loginStatePreview,
        onEventSent = { },
        onNavigationRequested = { }
    )
}

val loginStatePreview = LoginContract.State (
    login = "my login",
    password = "password",
    isSuccess = null,
    buttonEnabled = true,
    errorMessage = null
)