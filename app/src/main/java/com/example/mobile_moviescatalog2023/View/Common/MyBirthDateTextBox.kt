package com.example.mobile_moviescatalog2023.View.Common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBirthDateTextBox(
    value: String,
    isValid: Boolean,
    isError: Boolean,
    onSaveDateEvent: (date: Long?) -> Unit,
    onSaveTextEvent: (text: String) -> Unit,
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
                onEventSent = { date ->
                    onSaveDateEvent(date)
                }
            )
        }

        val maxLength = 10
        OutlinedTextField(
            value = value,
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            isError = isError,
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                if (it.length <= maxLength) {
                    onSaveTextEvent(it)
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

        if(isValid) {
            Text(
                text = stringResource(R.string.invalid_birth_date_message),
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