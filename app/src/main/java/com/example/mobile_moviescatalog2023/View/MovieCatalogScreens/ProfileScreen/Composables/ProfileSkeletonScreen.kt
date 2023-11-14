package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = spacedBy(15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Box(modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .shimmerEffect())
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(29.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .shimmerEffect()
            )
        }

        TextFieldBoxSkeleton()
        TextFieldBoxSkeleton()
        TextFieldBoxSkeleton()
        TextFieldBoxSkeleton()
        TextFieldBoxSkeleton()

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .clip(RoundedCornerShape(10.dp))
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .clip(RoundedCornerShape(10.dp))
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .height(18.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TextFieldBoxSkeleton() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(18.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainSkeletonScreenPreview() {
    MainSkeletonScreen()
}