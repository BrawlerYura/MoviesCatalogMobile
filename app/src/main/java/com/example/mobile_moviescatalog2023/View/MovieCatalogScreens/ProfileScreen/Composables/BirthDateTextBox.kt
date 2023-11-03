package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateTextBox(
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
            MyDatePicker(
                onDismiss = { openDialog.value = !openDialog.value },
                onEventSent
            )
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

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    onDismiss: () -> Unit,
    onEventSent: (ProfileScreenContract.Event) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }

    DatePickerDialog(
        onDismissRequest = {
            onDismiss()
        },
        colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    onEventSent(
                        ProfileScreenContract.Event.SaveBirthDateWithFormatEvent(
                            datePickerState.selectedDateMillis
                        )
                    )
                },
                enabled = confirmEnabled.value
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.refuse))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(showBackground = true)
@Composable
fun BirthDateTextBoxPreview() {
    BirthDateTextBox(
        state = ProfileScreenContract.State(
            id = "",
            nickName = null,
            email = "my@email.com",
            userIconUrl = null,
            name = "",
            gender = 0,
            birthDate = "30.07.2004",
            isSuccess = null
        ),
        onEventSent = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun MyDatePickerPreview() {
    MyDatePicker(
        onDismiss = { },
        onEventSent = { }
    )
}