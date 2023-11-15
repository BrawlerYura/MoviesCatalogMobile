package com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography

@Composable
fun LoginBottomText(onNavigationRequested: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 16.dp)) {
        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(
                text = stringResource(R.string.no_account_question),
                style = MyTypography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.registration_prompt),
                style = MyTypography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onNavigationRequested()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginBottomTextPreview() {
    LoginBottomText { }
}