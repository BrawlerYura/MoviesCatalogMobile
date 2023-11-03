package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun CancellButton(
    onEventSent: (ProfileScreenContract.Event) -> Unit
) {
    Button(
        onClick = {
            onEventSent(ProfileScreenContract.Event.LoadUserDetails)
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
}