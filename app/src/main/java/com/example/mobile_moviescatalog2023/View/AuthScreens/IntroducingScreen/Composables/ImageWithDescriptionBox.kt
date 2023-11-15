package com.example.mobile_moviescatalog2023.View.AuthScreens.IntroducingScreen.Composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography

@Composable
fun ImageWithDescriptionBox() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.screenphoto),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 35.dp, top = 56.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(R.string.world_of_cinema),
            style = MyTypography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(R.string.app_description),
            style = MyTypography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 8.dp, bottom = 35.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageWithDescriptionPreview() {
    ImageWithDescriptionBox()
}