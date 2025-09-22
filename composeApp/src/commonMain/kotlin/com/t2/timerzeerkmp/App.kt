package com.t2.timerzeerkmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.t2.timerzeerkmp.app.Route
import com.t2.timerzeerkmp.data.repository.TimerRepository
import com.t2.timerzeerkmp.domain.getLiveActivityManager
import com.t2.timerzeerkmp.presentation.fullScreenTimer.RootTimerFullScreen
import com.t2.timerzeerkmp.presentation.main.theme.TimerzeerTheme
import com.t2.timerzeerkmp.presentation.timerPreview.TimerScreenRoot
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.compose_multiplatform


private val liveActivityManager = getLiveActivityManager()

@Composable
@Preview
fun App() {
    TimerzeerTheme {

        AppNavHost()


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
    }
}


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
