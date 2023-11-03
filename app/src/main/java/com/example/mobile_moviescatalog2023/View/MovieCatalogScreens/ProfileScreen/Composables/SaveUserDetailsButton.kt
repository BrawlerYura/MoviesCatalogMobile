package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun SaveUserDetailsButton(
    onEventSent: (ProfileScreenContract.Event) -> Unit
) {
    Button(
        onClick = {
            onEventSent(ProfileScreenContract.Event.PutNewUserDetails)
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
}

@Preview(showBackground = true)
@Composable
private fun SaveUserDetailsButtonPreview() {
    SaveUserDetailsButton(
        onEventSent = { }
    )
}