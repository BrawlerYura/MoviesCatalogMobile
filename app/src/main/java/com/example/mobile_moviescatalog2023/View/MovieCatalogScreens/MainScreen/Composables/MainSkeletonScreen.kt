package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mobile_moviescatalog2023.R
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

        Box(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxWidth(0.3f)
                .height(30.dp)
                .clip(RoundedCornerShape(5.dp))
                .shimmerEffect()
        )

        for(i in 0..1) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier.width(95.dp).height(130.dp).clip(RoundedCornerShape(5.dp))
                        .shimmerEffect()
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .shimmerEffect()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(15.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = spacedBy(4.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .shimmerEffect()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .shimmerEffect()
                        )
                    }
                    Row(horizontalArrangement = spacedBy(4.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainSkeletonScreenPreview() {
    MainSkeletonScreen()
}