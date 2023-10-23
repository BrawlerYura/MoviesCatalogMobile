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
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.NavigationModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.LoginScreens.BottomRegistrationTextBox
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginHeader
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen.RegistrationPasswordEvent
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen.RegistrationPasswordViewModel
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun RegistrationPasswordScreen(navController: NavHostController) {
    val viewModel : RegistrationPasswordViewModel = viewModel(LocalViewModelStoreOwner.current!!)
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

                LoginHeader(navController)

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

                PasswordBox(viewModel)

                RepeatPassword(viewModel)

                Button(
                    onClick = {
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.finish_registration_button),
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
            BottomRegistrationTextBox(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepeatPassword(viewModel: RegistrationPasswordViewModel) {
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

        var passwordState by remember { mutableStateOf("") }
        var isTextHidden by remember { mutableStateOf(true) }
        OutlinedTextField(
            value = passwordState,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation =  if (isTextHidden) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                passwordState = it
                viewModel.send(RegistrationPasswordEvent.SaveRepeatedPasswordEvent(passwordState))
            },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { isTextHidden = !isTextHidden }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id =
                        if (isTextHidden) {R.drawable.opened_eye}
                        else {R.drawable.closed_eye}),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBox(viewModel: RegistrationPasswordViewModel) {
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

        var passwordState by remember { mutableStateOf("") }
        var isTextHidden by remember { mutableStateOf(true) }
        OutlinedTextField(
            value = passwordState,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation =  if (isTextHidden) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                passwordState = it
                viewModel.send(RegistrationPasswordEvent.SavePasswordEvent(passwordState))
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
