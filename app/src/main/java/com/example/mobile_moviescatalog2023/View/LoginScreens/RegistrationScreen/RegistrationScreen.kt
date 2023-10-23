package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@Composable
fun RegistrationScreen(navController: NavHostController) {
    val viewModel : RegistrationViewModel = viewModel(LocalViewModelStoreOwner.current!!)
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

                NameBox(viewModel)

                GenderBox(viewModel)

                LoginBox(viewModel)

                MailBox(viewModel)

                BirthDateBox(viewModel)

                Button(
                    onClick = {
                        navController.navigate(NavigationModel.MainScreens.RegistrationPasswordScreen.name)
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
            BottomRegistrationTextBox(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameBox(viewModel: RegistrationViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
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

        var name by remember { mutableStateOf("") }

        OutlinedTextField(
            value = name,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                name = it
                viewModel.send(RegistrationEvent.SaveNameEvent(name))
                            },
            singleLine = true,
            trailingIcon = {
                if (name.isNotEmpty()) {
                    IconButton(onClick = {
                        name = ""
                        viewModel.send(RegistrationEvent.SaveNameEvent(name))
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
fun MailBox(viewModel: RegistrationViewModel) {
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

        var mailText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = mailText,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                mailText = it
                viewModel.send(RegistrationEvent.SaveEmailEvent(mailText))
            },
            singleLine = true,
            trailingIcon = {
                if (mailText.isNotEmpty()) {
                    IconButton(onClick = {
                        mailText = ""
                        viewModel.send(RegistrationEvent.SaveEmailEvent(mailText))
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
fun BirthDateBox(viewModel: RegistrationViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
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

        var birthDateText by remember { mutableStateOf("") }
        val calendarState = UseCaseState()

        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true,
                style = CalendarStyle.MONTH
            ),
            selection = CalendarSelection.Date { date ->
                viewModel.send(RegistrationEvent.SaveBirthDateWithFormatEvent(date.toString()))
                birthDateText = viewModel.stateLive.value?.birthDate ?: ""
            }
        )

        val maxLength = 10
        OutlinedTextField(
            value = birthDateText,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                if (it.length <= maxLength) {
                    birthDateText = it
                    viewModel.send(RegistrationEvent.SaveBirthDateEvent(birthDateText))
                }
            },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { calendarState.show() }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderBox(viewModel: RegistrationViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
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
                    .background(MaterialTheme.colorScheme.surface),
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
                            viewModel.send(RegistrationEvent.SaveGenderEvent(
                                if(selectedIndex.value == 0) { "male" }
                                else { "female" }
                            ))
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedIndex.value == index) {
                                MaterialTheme.colorScheme.onBackground
                            } else {
                                MaterialTheme.colorScheme.surface
                            },
                            contentColor = if (selectedIndex.value == index)
                                Color(0xFF404040)
                            else
                                Color(0xFF909499)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBox(viewModel: RegistrationViewModel) {
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

        var loginTextState by remember { mutableStateOf("") }
        OutlinedTextField(
            value = loginTextState,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                loginTextState = it
                viewModel.send(RegistrationEvent.SaveLoginEvent(loginTextState))
            },
            singleLine = true,
            trailingIcon = {
                if (loginTextState.isNotEmpty()) {
                    IconButton(onClick = {
                        loginTextState = ""
                        viewModel.send(RegistrationEvent.SaveLoginEvent(loginTextState))
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