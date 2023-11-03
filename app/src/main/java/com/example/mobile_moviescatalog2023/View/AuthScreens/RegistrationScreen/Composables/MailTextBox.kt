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
fun MailTextBox(
    state: RegistrationContract.State,
    onEventSent: (event: RegistrationContract.Event) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.email_label),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = state.email,
            colors = OutlinedTextFieldDefaults.colors(
            ),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onEventSent(RegistrationContract.Event.SaveEmailEvent(it))
            },
            singleLine = true,
            trailingIcon = {
                if (state.email.isNotEmpty()) {
                    IconButton(onClick = {
                        onEventSent(RegistrationContract.Event.SaveEmailEvent(""))
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

@Preview(showBackground = true)
@Composable
private fun MailTextBoxPreview() {
    MailTextBox(
        state = RegistrationContract.State(
            name = "my name",
            gender = 0,
            login = "",
            email = "my@email.com",
            birthDate = "30.07.2004",
            apiBirthDate = ""
        ),
        onEventSent = { }
    )
}