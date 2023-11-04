package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.Composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun LoginTextBox(
    state: RegistrationContract.State,
    onEventSent: (event: RegistrationContract.Event) -> Unit
) {
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
            colors = OutlinedTextFieldDefaults.colors(
            ),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onEventSent(RegistrationContract.Event.SaveLoginEvent(it))
            },
            singleLine = true,
            trailingIcon = {
                if (state.login.isNotEmpty()) {
                    IconButton(onClick = {
                        onEventSent(RegistrationContract.Event.SaveLoginEvent(""))
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

        if(state.isLoginValid == false) {
            Text(
                text = stringResource(R.string.invalid_login_message),
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginTextBoxPreview() {
    LoginTextBox(
        state = registrationStatePreview,
        onEventSent = { }
    )
}