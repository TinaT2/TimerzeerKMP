package com.t2.timerzeerkmp.presentation.fullScreenTimer.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import timerzeerkmp.composeapp.generated.resources.Res

@Composable
fun LottieLoader(modifier: Modifier = Modifier, resId: String) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(resId).decodeToString()
        )
    }
    val progress by animateLottieCompositionAsState(composition)

    Image(
        modifier = modifier,
        painter = rememberLottiePainter(
            composition = composition,
            progress = { progress },
        ),
        contentScale = ContentScale.Fit,
        contentDescription = "Lottie animation"
    )
}