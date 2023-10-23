package com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.NavigationModel
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginBox
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginHeader
import com.example.mobile_moviescatalog2023.View.LoginScreens.PasswordBox
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun LoginScreen(navController: NavHostController) {
    LoginScreenInner(navController)
}

@Composable
fun LoginScreenInner(navController: NavHostController) {
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
                    text = stringResource(R.string.login_title),
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                LoginBox()

                PasswordBox()

                Button(
                    onClick = {
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
                            navController.navigate(NavigationModel.MainScreens.RegistrationScreen.name)
                        }
                    )
                }
            }
        }
    }
}