package com.example.mobile_moviescatalog2023.View.Common

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

@Composable
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(900)
        ), label = ""
    )

    val gradientColors =
        listOf(
            Color(0xFF292929),
            Color(0xFF2E2E2E),
            Color(0xFF3A3A3A),
            Color(0xFF2E2E2E),
            Color(0xFF292929),
        )

    val gradientStartOffsetX = startOffsetX - 300
    val gradientEndOffsetX = startOffsetX + size.width.toFloat() + 300

    background(
        brush = Brush.linearGradient(
            colors = gradientColors,
            start = Offset(gradientStartOffsetX, 0f),
            end = Offset(gradientEndOffsetX, size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}