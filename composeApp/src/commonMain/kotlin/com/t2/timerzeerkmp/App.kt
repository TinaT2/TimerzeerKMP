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
import com.t2.timerzeerkmp.app.Route
import com.t2.timerzeerkmp.data.repository.SettingsRepository
import com.t2.timerzeerkmp.data.repository.TimerRepository
import com.t2.timerzeerkmp.domain.getLiveActivityManager
import com.t2.timerzeerkmp.presentation.fullScreenTimer.RootTimerFullScreen
import com.t2.timerzeerkmp.presentation.main.components.SmoothStartUpAnimation
import com.t2.timerzeerkmp.presentation.main.theme.TimerzeerTheme
import com.t2.timerzeerkmp.presentation.main.theme.backgroundToIsDark
import com.t2.timerzeerkmp.presentation.main.theme.backgrounds
import com.t2.timerzeerkmp.presentation.main.theme.endingAnimations
import com.t2.timerzeerkmp.presentation.main.theme.fontStyles
import com.t2.timerzeerkmp.presentation.timerPreview.TimerScreenRoot
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject


private val liveActivityManager = getLiveActivityManager()

@Composable
@Preview
fun App() {
    TimerzeerTheme {
        val settingsRepository: SettingsRepository = koinInject()
        var fontStyleKeyResource by remember { mutableStateOf<String?>(null) }
        var currentThemeId by remember { mutableStateOf<String?>(null) }
        var endingAnimation by remember { mutableStateOf<StringResource?>(null) }
        var isEndingAnimationLoaded by remember { mutableStateOf(false) }
        var isFontLoaded by remember { mutableStateOf(false) }
        var isBackgroundLoaded by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
                settingsRepository.getFontStyleKeResource().collect {
                    fontStyleKeyResource = it
                    //todo isFontLoaded = true
                }
                settingsRepository.getBackgroundTheme().collect {backgroundKey->
                    currentThemeId = backgroundKey

                    isBackgroundLoaded = true
                }

                settingsRepository.getEndingAnimation().collect {endingAnimationKey->
                    endingAnimation =
                        endingAnimations.keys.find { it.key == endingAnimationKey }
                    isEndingAnimationLoaded = true
                }
        }
        SmoothStartUpAnimation(true) {
            TimerzeerTheme(
                darkTheme = backgroundToIsDark[backgrounds().keys.find { it.key == currentThemeId }] ?: isSystemInDarkTheme(),
                fontId = fontStyles().keys.find { it.key == fontStyleKeyResource },
                backgroundId =  backgrounds().keys.find { it.key == currentThemeId },
                endingAnimationId = endingAnimation
            ) {
                AppNavHost()
            }
        }
    }
}


//        var showContent by remember { mutableStateOf(false) }
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                    Button({
//                        liveActivityManager.start(10)
//                    }) {
//                        Text("Start")
//                    }
//
//                    Button({
//                        liveActivityManager.stop()
//                    }) {
//                        Text("Stop")
//                    }
//                }
//            }
//        }


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val repository: TimerRepository = koinInject()

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
                LaunchedEffect(Unit) {
                    if (repository.timerState.value.isRunning)
                        navController.navigate(
                            Route.TimerFullScreen
                        )
                }
                TimerScreenRoot {
                    navController.navigate(Route.TimerFullScreen)
                }
            }
            composable<Route.TimerFullScreen>(
                enterTransition = { fadeIn(animationSpec = tween(durationMillis = (1000))) },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = (1000))) }
            ) {

                RootTimerFullScreen {
                    navController.navigateUp()
                }
            }
        }
    }
}
