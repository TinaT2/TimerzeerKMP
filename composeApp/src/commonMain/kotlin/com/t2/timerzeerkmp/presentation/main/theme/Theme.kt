package com.t2.timerzeerkmp.presentation.main.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.tina.timerzeer.core.presentation.theme.BgPrimary
import com.tina.timerzeer.core.presentation.theme.BgPrimaryDark
import com.tina.timerzeer.core.presentation.theme.Error
import com.tina.timerzeer.core.presentation.theme.ErrorDark
import com.tina.timerzeer.core.presentation.theme.OnColorWhite
import com.tina.timerzeer.core.presentation.theme.OnColorWhiteDark
import com.tina.timerzeer.core.presentation.theme.Primary
import com.tina.timerzeer.core.presentation.theme.PrimaryDark
import com.tina.timerzeer.core.presentation.theme.Secondary
import com.tina.timerzeer.core.presentation.theme.SecondaryDark
import com.tina.timerzeer.core.presentation.theme.Tertiary
import com.tina.timerzeer.core.presentation.theme.TertiaryDark
import com.tina.timerzeer.core.presentation.theme.TextPrimary
import com.tina.timerzeer.core.presentation.theme.TextPrimaryDark
import com.tina.timerzeer.core.presentation.theme.TextSecondary
import com.tina.timerzeer.core.presentation.theme.TextSecondaryDark
import com.tina.timerzeer.core.presentation.theme.TextSecondaryTransparent
import org.jetbrains.compose.resources.StringResource

val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = BgPrimary,
    surface = OnColorWhite,
    error = Error,
    onPrimary = TextPrimary,
    onSecondary = TextSecondary,
)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    tertiary = TertiaryDark,
    background = BgPrimaryDark,
    surface = OnColorWhiteDark,
    error = ErrorDark,
    onPrimary = TextPrimaryDark,
    onSecondary = TextSecondaryDark,
)


@Composable
fun TimerzeerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    fontId: StringResource? = null,
    endingAnimationId: StringResource? = null,
    backgroundId: StringResource? = null,
    content: @Composable (() -> Unit)
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val isCustomized = backgrounds()[backgroundId] != null
    val customColors = if (isCustomized) {
        CustomColors(
            rowBackground = TextSecondaryTransparent,
            mainBackground = Color.Transparent,
            textColorEnabled = colorScheme.onPrimary,
            textColorDisabled = TextSecondary,
            border = MaterialTheme.colorScheme.surface
        )
    } else {
        CustomColors(
            rowBackground = colorScheme.tertiary,
            textColorEnabled = colorScheme.onPrimary,
            mainBackground = colorScheme.background,
            textColorDisabled = colorScheme.onSecondary,
            border = colorScheme.tertiary
        )
    }

    val customGraphicIds = CustomGraphicIds(
        backgroundId = backgroundId,
        fontId = fontId ?: DefaultLocalCustomGraphicIds.fontId,
        endingAnimationId = endingAnimationId ?: DefaultLocalCustomGraphicIds.endingAnimationId
    )

    val typoGraphy =
        buildTypography(fontStyles()[fontId ?: DefaultLocalCustomGraphicIds.fontId] ?: fontManrope())

    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        LocalCustomGraphicIds provides customGraphicIds
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typoGraphy,
            content = content
        )
    }

}