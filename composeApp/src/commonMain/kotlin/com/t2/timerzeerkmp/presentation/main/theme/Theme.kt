package com.t2.timerzeerkmp.presentation.main.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tina.timerzeer.core.presentation.theme.SizeXL
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
            border = MaterialTheme.colorScheme.surface,
            buttonBackgroundEnabled = colorScheme.primary,
            buttonBackgroundDisabled = TextSecondaryTransparent
        )
    } else {
        CustomColors(
            rowBackground = colorScheme.tertiary,
            textColorEnabled = colorScheme.onPrimary,
            mainBackground = colorScheme.background,
            textColorDisabled = colorScheme.onSecondary,
            border = colorScheme.tertiary,
            buttonBackgroundEnabled = colorScheme.primary,
            buttonBackgroundDisabled = colorScheme.tertiary
        )
    }

    val customGraphicIds = CustomGraphicIds(
        backgroundId = backgroundId,
        fontId = fontId ?: DefaultLocalCustomGraphicIds.fontId,
        endingAnimationId = endingAnimationId ?: DefaultLocalCustomGraphicIds.endingAnimationId
    )

    val typoGraphy =
        buildTypography(
            fontStyles()[fontId ?: DefaultLocalCustomGraphicIds.fontId] ?: fontManrope()
        )

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

@Composable
fun ColorfulScaffold(
    onWholeScreenClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    val customGraphicIds = LocalCustomGraphicIds.current
    val customColors = LocalCustomColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onWholeScreenClicked()
            }) {

        backgrounds()[customGraphicIds.backgroundId]?.invoke()

        Scaffold(
            modifier = modifier
                .background(customColors.mainBackground)
                .padding(top = SizeXL),
            containerColor = customColors.mainBackground,
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            contentWindowInsets = contentWindowInsets,
            contentColor = contentColor
        ) { paddingValues ->
            content.invoke(paddingValues)
        }
    }
}