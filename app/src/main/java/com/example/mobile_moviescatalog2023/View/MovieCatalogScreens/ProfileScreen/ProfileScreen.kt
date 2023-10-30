package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.BottomNavigationBar
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: ProfileScreenContract.Effect.Navigation) -> Unit,
    onBottomNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
) {
    if(state.isSuccess == null) {
        onEventSent(ProfileScreenContract.Event.LoadUserDetails)
    }

    FilmusTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    onBottomNavigationRequested,
                    2
                )
            }
        ) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                ProfileBox(state, onEventSent)
                MailBox(state, onEventSent)
                ProfileIconUrlBox(state, onEventSent)
                NameBox(state, onEventSent)
                GenderBox(state, onEventSent)
                BirthDateBox(state, onEventSent)

                Button(
                    onClick = {
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W600,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        )
                    )
                }

                Button(
                    onClick = {
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth()
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF292929),
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.refuse),
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.W600,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        ),
                    )
                }

                Text(
                    text = stringResource(R.string.logout),
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .clickable {
                        onEventSent(ProfileScreenContract.Event.Logout)
                        onNavigationRequested(ProfileScreenContract.Effect.Navigation.ToIntroducing)
                    }
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(67.dp))
            }
        }
    }
}

@Composable
fun ProfileBox(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit
) {
    Column(
        modifier = Modifier.padding(bottom = 20.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        AsyncImage(
            model = state.userIconUrl ?: R.drawable.logo,
            contentDescription = null,
            modifier = Modifier.height(88.dp).width(88.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = state.name,
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W700,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameBox(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit
) {
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

        OutlinedTextField(
            value = state.name,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onEventSent(ProfileScreenContract.Event.SaveNameEvent(it))
            },
            singleLine = true,
            trailingIcon = {
                if (state.name.isNotEmpty()) {
                    IconButton(onClick = {
                        onEventSent(ProfileScreenContract.Event.SaveNameEvent(""))
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
fun ProfileIconUrlBox(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.url_to_profile_icon),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.userIconUrl ?: "",
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onEventSent(ProfileScreenContract.Event.SaveUserIconUrl(it))
            },
            singleLine = true,
            trailingIcon = {
                if (state.name.isNotEmpty()) {
                    IconButton(onClick = {
                        onEventSent(ProfileScreenContract.Event.SaveUserIconUrl(""))
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
fun MailBox(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit
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
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onEventSent(ProfileScreenContract.Event.SaveEmailEvent(it))
            },
            singleLine = true,
            trailingIcon = {
                if (state.email.isNotEmpty()) {
                    IconButton(onClick = {
                        onEventSent(ProfileScreenContract.Event.SaveEmailEvent(""))
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

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateBox(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
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
        if (openDialog.value) {
            val datePickerState = rememberDatePickerState()
            val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
            DatePickerDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onEventSent(ProfileScreenContract.Event.SaveBirthDateWithFormatEvent(datePickerState.selectedDateMillis))
                        },
                        enabled = confirmEnabled.value
                    ) {
                        Text(stringResource(R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(stringResource(R.string.refuse))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        val maxLength = 10
        OutlinedTextField(
            value = state.birthDate,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                if (it.length <= maxLength) {
                    onEventSent(ProfileScreenContract.Event.SaveBirthDateEvent(it))
                }
            },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { openDialog.value = true }) {
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
fun GenderBox(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit
) {
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
        val items = listOf(stringResource(R.string.male), stringResource(R.string.female))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp),
                    onClick = {
                        onEventSent(ProfileScreenContract.Event.SaveGenderEvent(index))
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.gender == index) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                        contentColor = if (state.gender == index)
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