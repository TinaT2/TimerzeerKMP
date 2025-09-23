package com.t2.timerzeerkmp.presentation.main.theme.backgrounds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

val Galaxy: @Composable () -> Unit = {
    val circleSize = 284.dp
    val blurRadius = 200.dp
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180027))
    ) {
        // Circle 1
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .offset(x = (maxWidth - 300.dp), y = (-65).dp)
                .size(284.dp)
                .blur(radius = blurRadius, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    color = Color(0xFF0D4C94),
                    shape = RoundedCornerShape(size = circleSize)
                )
                .clip(CircleShape)
        )

        Box(
            Modifier
                .offset(x = (-86).dp)
                .align(Alignment.CenterStart)
                .size(circleSize)
                .blur(radius = blurRadius, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    color = Color(0xFF550A80),
                    shape = RoundedCornerShape(size = circleSize)
                )
                .clip(CircleShape)
        )

        // Circle 3
        Box(
            Modifier
                .offset(x = (maxWidth - 300.dp))
                .align(Alignment.BottomEnd)
                .size(circleSize)
                .blur(radius = blurRadius, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    color = Color(0xFF03471D),
                    shape = RoundedCornerShape(size = circleSize)
                )
                .clip(CircleShape)
        )
    }
}

@Preview
@Composable
fun GlassCirclesPreview() {
    Galaxy()
}
