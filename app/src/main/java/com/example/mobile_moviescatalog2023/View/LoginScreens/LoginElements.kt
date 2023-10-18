package com.example.mobile_moviescatalog2023.View.LoginScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBox() {
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

        var textState by remember { mutableStateOf("") }
        var isTextHidden by remember { mutableStateOf(true) }
        val maxLength = 100
        OutlinedTextField(
            value = textState,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation =  if (isTextHidden) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                if (it.length <= maxLength) textState = it
            },
            singleLine = true,
            trailingIcon = {
                if (textState.isNotEmpty()) {
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
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BottomRegistrationTextBox(navController : NavHostController) {
    Box (modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)){
        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(
                text = stringResource(R.string.already_have_account_question),
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
            Text(
                text = stringResource(R.string.login_prompt),
                style = TextStyle(
                    fontFamily = interFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.clickable {
//                    navController.navigate(NavigationModel.MainScreens.LoginScreen.name)
                }
            )
        }
    }
}


@Composable
fun LoginHeader(navController: NavHostController) {
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
                        navController.popBackStack()
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