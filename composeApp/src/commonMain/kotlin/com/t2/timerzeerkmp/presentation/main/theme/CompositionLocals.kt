package com.t2.timerzeerkmp.presentation.main.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.value_default


@Immutable
data class CustomColors(
    val rowBackground: Color,
    val mainBackground: Color,
    val textColorEnabled: Color,
    val textColorDisabled: Color,
    val border: Color
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        rowBackground = Color.Gray,
        mainBackground = Color.Transparent,
        textColorEnabled = Color.Black,
        textColorDisabled = Color.DarkGray,
        border = Color.DarkGray,
    )
}

@Immutable
data class CustomGraphicIds(
    val backgroundId: StringResource?,
    val endingAnimationId : StringResource,
    val fontId: StringResource
)

val LocalCustomGraphicIds = staticCompositionLocalOf {
    DefaultLocalCustomGraphicIds
}

val DefaultLocalCustomGraphicIds =  CustomGraphicIds(
    backgroundId = null,
    endingAnimationId = endingAnimations.keys.first(),
    fontId =  Res.string.value_default
)