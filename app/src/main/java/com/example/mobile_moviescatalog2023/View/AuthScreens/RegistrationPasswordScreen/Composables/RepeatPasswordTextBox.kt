package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.Composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun RepeatPasswordTextBox(
    state: RegistrationPasswordContract.State,
    onEventSent: (event: RegistrationPasswordContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.repeat_password),
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
            colors = OutlinedTextFieldDefaults.colors(
            ),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onEventSent(RegistrationPasswordContract.Event.SavePasswordEvent(it))
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

@Preview(showBackground = true)
@Composable
private fun RepeatPasswordTextBoxPreview() {
    RepeatPasswordTextBox(
        state = RegistrationPasswordContract.State (
            password = "",
            repPassword = "password",
            userName = "",
            name = "",
            email = "",
            birthDate = "",
            gender = 0,
            isSuccess = null
        ),
        onEventSent = { }
    )
}