package com.t2.timerzeerkmp.presentation.main.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.manrope_bold
import timerzeerkmp.composeapp.generated.resources.manrope_extrabold
import timerzeerkmp.composeapp.generated.resources.manrope_extralight
import timerzeerkmp.composeapp.generated.resources.manrope_light
import timerzeerkmp.composeapp.generated.resources.manrope_medium
import timerzeerkmp.composeapp.generated.resources.manrope_regular
import timerzeerkmp.composeapp.generated.resources.manrope_semibold
import timerzeerkmp.composeapp.generated.resources.play_write_us_modern_regular
import timerzeerkmp.composeapp.generated.resources.roboto_regular
import timerzeerkmp.composeapp.generated.resources.saira_regular


@Composable
fun fontManrope() = FontFamily(
    Font(Res.font.manrope_regular, FontWeight.Normal),
    Font(Res.font.manrope_medium, FontWeight.Medium),
    Font(Res.font.manrope_semibold, FontWeight.SemiBold),
    Font(Res.font.manrope_bold, FontWeight.Bold),
    Font(Res.font.manrope_extrabold, FontWeight.ExtraBold),
    Font(Res.font.manrope_light, FontWeight.Light),
    Font(Res.font.manrope_extralight, FontWeight.ExtraLight),
)

@Composable
fun fontPlayWrite() = FontFamily(
    Font(Res.font.play_write_us_modern_regular, FontWeight.Normal)
)

@Composable
fun fontMinimal() = FontFamily(Font(Res.font.roboto_regular, FontWeight.Normal))

@Composable
fun fontSaira() = FontFamily(Font(Res.font.saira_regular, FontWeight.Normal))


// Set of Material typography styles to start with
fun buildTypography(fontFamily: FontFamily) = Typography(
    headlineLarge = TextStyle(
        fontSize = 42.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight(700),
    ),
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight(700),
    ),
    headlineSmall = TextStyle(
        fontSize = 20.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight(600),
    ),
    bodyLarge = TextStyle(
        fontSize = 22.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight(500),
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight(500),
    ),
    labelLarge = TextStyle(
        fontSize = 12.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight(500),
    )
)
