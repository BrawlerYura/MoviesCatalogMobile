package com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginHeader(onNavigationRequested: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(24.dp)
    ) {
        var isClickable by remember { mutableStateOf(true) }

        Icon(
            painter = painterResource(R.drawable.back_icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.height(12.dp).width(12.dp).align(Alignment.CenterStart)
                .clickable {
                    if (isClickable) {
                        isClickable = false
                        onNavigationRequested()
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

@Preview(showBackground = true)
@Composable
private fun LoginHeaderPreview() {
    LoginHeader { }
}