package com.t2.timerzeerkmp.presentation.main.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.t2.timerzeerkmp.presentation.timerPreview.DEFAULT_NAME
import org.jetbrains.compose.resources.StringResource


@Immutable
data class CustomColors(
    val rowBackground: Color,
    val mainBackground: Color,
    val textColorEnabled: Color,
    val textColorDisabled: Color,
    val border: Color,
    val buttonBackgroundEnabled:Color,
    val buttonBackgroundDisabled:Color
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        rowBackground = Color.Gray,
        mainBackground = Color.Transparent,
        textColorEnabled = Color.Black,
        textColorDisabled = Color.DarkGray,
        border = Color.DarkGray,
        buttonBackgroundEnabled = Color.Blue,
        buttonBackgroundDisabled = Color.Transparent
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
    endingAnimationId = DEFAULT_NAME,
    fontId = DEFAULT_NAME
)