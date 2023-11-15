package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Common.FullScreenImageDialog
import com.example.mobile_moviescatalog2023.View.Common.PreviewStateBuilder.profileStatePreview
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography
import com.example.mobile_moviescatalog2023.ui.theme.interFamily
import kotlinx.coroutines.delay

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileBox(
    state: ProfileScreenContract.State,
    onImageClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 5.dp, top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        GlideImage(
            model = if(state.userIconUrl != "") {
                state.userIconUrl ?: R.drawable.logo
            } else {
                R.drawable.logo
            },
            contentDescription = null,
            modifier = Modifier.height(88.dp).width(88.dp).clip(CircleShape).clickable(
                onClick = {
                    if(state.userIconUrl != "" && state.userIconUrl != null) {
                        onImageClicked()
                    }
                }
            ),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = state.nickName ?: "",
            style = MyTypography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ProfileBoxPreview() {
    ProfileBox(
        state = profileStatePreview,
        onImageClicked = { }
    )
}