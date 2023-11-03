package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderTextBox(
    state: ProfileScreenContract.State,
    onEventSent: (event: ProfileScreenContract.Event) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.gender_label),
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        val items = listOf(stringResource(R.string.male), stringResource(R.string.female))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp),
                    onClick = {
                        onEventSent(ProfileScreenContract.Event.SaveGenderEvent(index))
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.gender == index) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                        contentColor = if (state.gender == index)
                            Color(0xFF404040)
                        else
                            Color(0xFF909499)
                    ),
                    shape = RoundedCornerShape(7.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenderTextBoxPreview() {
    GenderTextBox(
        state = ProfileScreenContract.State(
            id = "",
            nickName = null,
            email = "my@email.com",
            userIconUrl = null,
            name = "my name",
            gender = 0,
            birthDate = "",
            isSuccess = null
        ),
        onEventSent = { }
    )
}