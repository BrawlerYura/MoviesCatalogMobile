package com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.SplashScreen.SplashContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

                LoginHeader(onNavigationRequested)

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

                LoginBox(state, onEventSent)

                PasswordBox(state, onEventSent)

                val context = LocalContext.current
                Button(
                    onClick = {
                        onEventSent(LoginContract.Event.SignIn(state.login, state.password, context))
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.login_button),
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W600,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        ),
                    )
                }
            }
            Box (modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)){
                Row(modifier = Modifier.align(Alignment.BottomCenter)) {
                    Text(
                        text = stringResource(R.string.no_account_question),
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W600,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                    )
                    Text(
                        text = stringResource(R.string.registration_prompt),
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W600,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier.clickable {
                            onNavigationRequested(LoginContract.Effect.Navigation.ToRegistration)
                        }
                    )
                }
            }
        }

        if(state.isTriedToSignIn and state.isSuccess) {
            onNavigationRequested(LoginContract.Effect.Navigation.ToMain)
        } else if(state.isTriedToSignIn and !state.isSuccess) {

        }
    }
}

@Composable
fun LoginHeader(onNavigationRequested: (navigationEffect: LoginContract.Effect.Navigation) -> Unit) {
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(24.dp)
    ) {
        var isClickable by remember { mutableStateOf(true) }

        Image(
            painter = painterResource(R.drawable.back_icon),
            contentDescription = null,
            modifier = Modifier.height(12.dp).width(12.dp).align(Alignment.CenterStart)
                .clickable {
                    if (isClickable) {
                        isClickable = false
                        onNavigationRequested(LoginContract.Effect.Navigation.Back)
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000L)
                            isClickable = true
                        }
                    }
                }
        )
        Text(
            text = stringResource(R.string.app_name),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W600,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBox(state: LoginContract.State, onEventSent: (event: LoginContract.Event) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.login_label),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.login,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onEventSent(LoginContract.Event.SaveLoginEvent(it))
            },
            singleLine = true,
            trailingIcon = {
                if (state.login.isNotEmpty()) {
                    IconButton(onClick = {
                        onEventSent(LoginContract.Event.SaveLoginEvent(""))
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBox(state: LoginContract.State, onEventSent: (event: LoginContract.Event) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.password_label),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        var isTextHidden by remember { mutableStateOf(true) }
        OutlinedTextField(
            value = state.password,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation =  if (isTextHidden) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onEventSent(LoginContract.Event.SavePasswordEvent(it))
            },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { isTextHidden = !isTextHidden }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id =
                        if (isTextHidden) {
                            R.drawable.opened_eye}
                        else {
                            R.drawable.closed_eye}),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
