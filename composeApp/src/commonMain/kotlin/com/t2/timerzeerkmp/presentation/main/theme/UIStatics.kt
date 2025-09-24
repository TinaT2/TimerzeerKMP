package com.t2.timerzeerkmp.presentation.main.theme

import androidx.compose.runtime.Composable
import com.t2.timerzeerkmp.presentation.main.theme.backgrounds.Digital
import com.t2.timerzeerkmp.presentation.main.theme.backgrounds.Galaxy
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.*

val endingAnimations = linkedMapOf(
    Res.string.value_default to "files/colorful.json",
    Res.string.ending_animation_Explosives to "files/firework.json",
    Res.string.ending_animation_fly_ribbons to "files/colorful.json",
)

val backgrounds = linkedMapOf(
    Res.string.value_default to null,
    Res.string.background_theme_dark to null,
    Res.string.background_theme_galaxy to Galaxy,
    Res.string.background_theme_digital to Digital,
)

val backgroundToIsDark = linkedMapOf(
    Res.string.value_default to false,
    Res.string.background_theme_dark to true,
    Res.string.background_theme_galaxy to true,
    Res.string.background_theme_digital to true
)
@Composable
fun fontStyles() = linkedMapOf(
    Res.string.value_default to fontManrope(),
    Res.string.timerstyle_classic to fontPlayWrite(),
    Res.string.timerstyle_minimal to fontMinimal(),
    Res.string.timerstyle_digital to fontSaira(),
)
