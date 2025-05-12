package com.attt.visitaapp.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun GradientCircle(modifier: Modifier, size:Int, colors: List<Color>) {
    Box(
        modifier = modifier
            .size(size.dp) // Circle size
            .clip(CircleShape) // Ensure it's circular
            .drawBehind {
                drawCircleWithGradient(colors)
            }
    )
}

fun DrawScope.drawCircleWithGradient(colors:List<Color>){
    drawCircle(
        brush = Brush.linearGradient(
            colors = colors // Gradient colors
        ),
        radius = size.minDimension / 2 // Ensure it fills the circle
    )
}