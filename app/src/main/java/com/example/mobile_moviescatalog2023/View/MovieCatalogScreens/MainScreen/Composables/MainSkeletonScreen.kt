package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_moviescatalog2023.View.Common.shimmerEffect
import com.example.mobile_moviescatalog2023.ui.theme.interFamily

@Composable
fun MainSkeletonScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Absolute.spacedBy(15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(497.dp).shimmerEffect())

        Text(
            text = "                           ",
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.W700,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .clip(RoundedCornerShape(5.dp))
                .shimmerEffect()
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Absolute.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier.width(95.dp).height(130.dp).clip(RoundedCornerShape(5.dp)).shimmerEffect())
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Absolute.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text =  "                                  " +
                            "                                  ",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .shimmerEffect()
                )

                Text(
                    text = "                 ",
                    style = TextStyle(
                        fontFamily = interFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainSkeletonScreenPreview() {
    MainSkeletonScreen()
}