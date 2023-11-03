package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun ProfileBox(
    state: ProfileScreenContract.State
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
            text = state.nickName ?: "",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W700,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileBoxPreview() {
    ProfileBox(
        state = ProfileScreenContract.State(
            id = "",
            nickName = "BrawlerYura",
            email = "my@email.com",
            userIconUrl = null,
            name = "",
            gender = 0,
            birthDate = "",
            isSuccess = null
        )
    )
}