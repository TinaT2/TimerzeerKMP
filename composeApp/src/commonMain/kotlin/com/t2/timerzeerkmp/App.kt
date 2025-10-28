package com.t2.timerzeerkmp

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.t2.timerzeerkmp.app.Route
import com.t2.timerzeerkmp.data.repository.SettingsRepository
import com.t2.timerzeerkmp.presentation.fullScreenTimer.RootTimerFullScreen
import com.t2.timerzeerkmp.presentation.main.components.SmoothStartUpAnimation
import com.t2.timerzeerkmp.presentation.main.theme.TimerzeerTheme
import com.t2.timerzeerkmp.presentation.main.theme.backgroundToIsDark
import com.t2.timerzeerkmp.presentation.main.theme.backgrounds
import com.t2.timerzeerkmp.presentation.main.theme.endingAnimations
import com.t2.timerzeerkmp.presentation.main.theme.fontStyles
import com.t2.timerzeerkmp.presentation.timerPreview.TimerScreenRoot
import kotlinx.coroutines.flow.combine
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    TimerzeerTheme {
        val settingsRepository: SettingsRepository = koinInject()
        var fontStyleKeyResource by remember { mutableStateOf<String?>(null) }
        var currentThemeId by remember { mutableStateOf<String?>(null) }
        var endingAnimation by remember { mutableStateOf<StringResource?>(null) }
        var isLoaded by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            combine(
                settingsRepository.getFontStyleKeResource(),
                settingsRepository.getBackgroundTheme(),
                settingsRepository.getEndingAnimation()
            ) { font, background, animation ->
                Triple(font, background, animation)
            }.collect { (font, background, animation) ->
                fontStyleKeyResource = font
                currentThemeId = background
                endingAnimation = endingAnimations.keys.find { it.key == animation }
                isLoaded = true
            }
        }
        //todo isLoaded
        SmoothStartUpAnimation(isLoaded) {
            TimerzeerTheme(
                darkTheme = backgroundToIsDark[backgrounds().keys.find { it.key == currentThemeId }]
                    ?: isSystemInDarkTheme(),
                fontId = fontStyles().keys.find { it.key == fontStyleKeyResource },
                backgroundId = backgrounds().keys.find { it.key == currentThemeId },
                endingAnimationId = endingAnimation
            ) {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.TimerGraph
    ) {
        navigation<Route.TimerGraph>(
            startDestination = Route.TimerPreview
        ) {
            composable<Route.TimerPreview>(
                enterTransition = { fadeIn(animationSpec = tween(durationMillis = (1000))) },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = (1000))) }
            ) {
                TimerScreenRoot { mode, title, initTime ->
                    navController.navigate(Route.TimerFullScreen(mode?.name, title, initTime))
                }
            }
            composable<Route.TimerFullScreen>(
                enterTransition = { fadeIn(animationSpec = tween(durationMillis = (1000))) },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = (1000))) }
            ) { backStackEntry ->
                val timerInitiate: Route.TimerFullScreen = backStackEntry.toRoute()
                RootTimerFullScreen(timerInitiate) {
                    navController.navigateUp()
                }
            }
        }
    }
}
