package com.example.mobile_moviescatalog2023.View.Common

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun MyTextFieldBox(
    value: String,
    isError: Boolean,
    isValid: Boolean,
    onSaveEvent: (text: String) -> Unit,
    headerText: String,
    errorText: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = headerText,
            style = MyTypography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                errorContainerColor = Color(0xFFE64646).copy(alpha = 0.1f)
            ),
            textStyle = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp
            ),
            onValueChange = {
                onSaveEvent(it)
            },
            singleLine = true,
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(onClick = {
                        onSaveEvent("")
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

        if (isValid) {
            Text(
                text = errorText,
                style = MyTypography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MailTextBoxPreview() {
    MyTextFieldBox(
        value = "value",
        isError = true,
        isValid = false,
        onSaveEvent = { },
        headerText = "Электронная почта",
        errorText = "Неверно введена почта"
    )
}