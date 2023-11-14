package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.ui.theme.MyTypography
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun FilmDescription(
    movieDescription: String?
) {
    if (movieDescription != null && movieDescription != "-") {
        var expanded by remember { mutableStateOf(false) }
        var boxHeight by remember { mutableStateOf(0.dp) }
        val localDensity = LocalDensity.current
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        if (!expanded) {
                            boxHeight = with(localDensity) { coordinates.size.height.toDp() }
                        }
                    }
            ) {
                Text(
                    text = movieDescription,
                    style = MyTypography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = if (expanded) Int.MAX_VALUE else 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, end = 15.dp, start = 15.dp)
                        .clickable { expanded = !expanded }
                )

                if (!expanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(boxHeight)
                            .background(
                                brush = Brush.verticalGradient(
                                    0f to Color.Transparent,
                                    0.9f to MaterialTheme.colorScheme.background
                                )
                            )
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp)
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.Absolute.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) {
                        stringResource(R.string.hide)
                    } else {
                        stringResource(R.string.more)
                    },
                    style = MyTypography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = if (expanded) Int.MAX_VALUE else 3,
                )
                if (!expanded) {
                    Icon(
                        painterResource(R.drawable.arrow_down),
                        null,
                        modifier = Modifier.width(10.dp).height(10.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun FilmDescriptionPreview() {
    FilmDescription(
        movieDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
                "ut aliquip ex ea commodo consequat."
    )
}