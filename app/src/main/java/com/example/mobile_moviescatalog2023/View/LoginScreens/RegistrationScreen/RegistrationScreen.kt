package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(navController: NavHostController) {
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
                    )
                )

                NameBox()

                GenderBox()

                LoginBox()

                MailBox()

                BirthDateBox()

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
                        text = stringResource(R.string.continue_button),
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
            Box(modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)) {
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
//                            navController.navigate(NavigationModel.MainScreens.RegistrationScreen.name)
                        }
                    )
                }
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameBox() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.name_label),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        var textState by remember { mutableStateOf("") }
        val maxLength = 100
        OutlinedTextField(
            value = textState,
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
                    IconButton(onClick = { textState = "" }) {
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
fun LoginBox() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 20.dp),
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

        var textState by remember { mutableStateOf("") }
        val maxLength = 100
        OutlinedTextField(
            value = textState,
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
                    IconButton(onClick = { textState = "" }) {
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
fun MailBox() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 20.dp),
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

        var textState by remember { mutableStateOf("") }
        val maxLength = 100
        OutlinedTextField(
            value = textState,
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
                    IconButton(onClick = { textState = "" }) {
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
fun BirthDateBox() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.birth_date),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        var textState by remember { mutableStateOf("") }
        val maxLength = 100
        OutlinedTextField(
            value = textState,
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
                    IconButton(onClick = { textState = "" }) {
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
fun GenderBox() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.gender_label),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        val selectedIndex = remember { mutableStateOf(0) }
        val itemIndex = remember { mutableStateOf(0) }
        val items = listOf("Мужчина", "Женщина")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.secondary),
                horizontalArrangement = Arrangement.Center
            ) {
                items.forEachIndexed { index, item ->
                    itemIndex.value = index
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp),
                        onClick = {
                            selectedIndex.value = index
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedIndex.value == index) {
                                MaterialTheme.colorScheme.background
                            } else {
                                MaterialTheme.colorScheme.secondary
                            },
                            contentColor = if (selectedIndex.value == index)
                                MaterialTheme.colorScheme.scrim
                            else
                                MaterialTheme.colorScheme.onSecondary
                        ),
                        shape = RoundedCornerShape(7.dp),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item,
                                textAlign = TextAlign.Center,
                                )
                        }
                    }
                }
            }

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
