package com.t2.timerzeerkmp.presentation.fullScreenTimer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.t2.timerzeerkmp.app.Route
import com.t2.timerzeerkmp.data.mapper.toDisplayString
import com.t2.timerzeerkmp.data.mapper.toTimeComponents
import com.t2.timerzeerkmp.domain.timer.TimerMode
import com.t2.timerzeerkmp.domain.timer.TimerState
import com.t2.timerzeerkmp.domain.util.HandleBackPress
import com.t2.timerzeerkmp.domain.util.LocalUtil
import com.t2.timerzeerkmp.domain.util.ShareExtension
import com.t2.timerzeerkmp.domain.util.ToastHandler
import com.t2.timerzeerkmp.presentation.fullScreenTimer.FullScreenTimerViewModel.Companion.COUNTDOWN_DONE_DELAY_MS
import com.t2.timerzeerkmp.presentation.fullScreenTimer.components.LottieLoader
import com.t2.timerzeerkmp.presentation.main.components.CaptionTextField
import com.t2.timerzeerkmp.presentation.main.components.HeadlineMediumTextField
import com.t2.timerzeerkmp.presentation.main.components.LightDarkPreviews
import com.t2.timerzeerkmp.presentation.main.components.RoundIconFilledMedium
import com.t2.timerzeerkmp.presentation.main.components.RoundIconOutlinedSmall
import com.t2.timerzeerkmp.presentation.main.components.SmoothFieldFadeAnimatedVisibility
import com.t2.timerzeerkmp.presentation.main.components.ThemedPreview
import com.t2.timerzeerkmp.presentation.main.theme.LocalCustomColors
import com.t2.timerzeerkmp.presentation.main.theme.LocalCustomGraphicIds
import com.t2.timerzeerkmp.presentation.main.theme.backgrounds
import com.t2.timerzeerkmp.presentation.main.theme.endingAnimations
import com.t2.timerzeerkmp.presentation.timerPreview.components.TimeSelector
import com.tina.timerzeer.core.presentation.theme.SizeS
import com.tina.timerzeer.core.presentation.theme.SizeXL
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.days
import timerzeerkmp.composeapp.generated.resources.hide_ui
import timerzeerkmp.composeapp.generated.resources.hold_for_3_seconds
import timerzeerkmp.composeapp.generated.resources.hours
import timerzeerkmp.composeapp.generated.resources.lock
import timerzeerkmp.composeapp.generated.resources.minutes
import timerzeerkmp.composeapp.generated.resources.my_timer_state
import timerzeerkmp.composeapp.generated.resources.pause
import timerzeerkmp.composeapp.generated.resources.play
import timerzeerkmp.composeapp.generated.resources.powered_by
import timerzeerkmp.composeapp.generated.resources.property_1_eye
import timerzeerkmp.composeapp.generated.resources.property_1_eye_off
import timerzeerkmp.composeapp.generated.resources.property_1_lock_01
import timerzeerkmp.composeapp.generated.resources.property_1_lock_unlocked_01
import timerzeerkmp.composeapp.generated.resources.property_1_pause_circle
import timerzeerkmp.composeapp.generated.resources.property_1_play
import timerzeerkmp.composeapp.generated.resources.property_1_share_06
import timerzeerkmp.composeapp.generated.resources.property_1_stop
import timerzeerkmp.composeapp.generated.resources.seconds
import timerzeerkmp.composeapp.generated.resources.share
import timerzeerkmp.composeapp.generated.resources.show_ui
import timerzeerkmp.composeapp.generated.resources.stop
import timerzeerkmp.composeapp.generated.resources.timezeer
import timerzeerkmp.composeapp.generated.resources.titleIcon
import timerzeerkmp.composeapp.generated.resources.value_default

@Composable
fun RootTimerFullScreen(
    timerInitiate: Route.TimerFullScreen,
    viewModel: FullScreenTimerViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    val timerState = viewModel.fullState.collectAsStateWithLifecycle()

    HandleBackPress {
        viewModel.onTimerIntent(TimerFullScreenIntent.Stop)
        onNavigateBack()
    }

    LaunchedEffect(viewModel.fullState.value.timer.isRunning) {
        if (!viewModel.fullState.value.timer.isRunning)
            viewModel.onTimerIntent(TimerFullScreenIntent.Start(timerInitiate))
    }
    TimerStarted(timerState.value, onTimerIntent = {
        viewModel.onTimerIntent(it)
    }, onNavigateBack = onNavigateBack)
}

@Composable
fun TimerStarted(
    timerState: FullScreenTimerState,
    onTimerIntent: (TimerFullScreenIntent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val customGraphicIds = LocalCustomGraphicIds.current
    val customColors = LocalCustomColors.current
    val coroutineScope = rememberCoroutineScope()
    val holdForSecondsString = stringResource(Res.string.hold_for_3_seconds)

    LaunchedEffect(timerState.timer.isCountDownDone) {
        if (timerState.timer.isCountDownDone) {
            delay(COUNTDOWN_DONE_DELAY_MS)
            onNavigateBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onTimerIntent(TimerFullScreenIntent.IconAppear)
            }) {

        backgrounds()[customGraphicIds.backgroundId]?.invoke()

        Scaffold(
            modifier = Modifier
                .background(customColors.mainBackground)
                .padding(top = SizeXL),
            containerColor = customColors.mainBackground
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.weight(1.3f))

                HeadlineMediumTextField(timerState.timer.title)

                Box(
                    modifier = Modifier
                        .weight(2f) // the space you already give to timer
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SmoothFieldFadeAnimatedVisibility(
                        visible = timerState.timer.isCountDownDone
                    ) {
                        LottieLoader(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .zIndex(2f),
                            resId = endingAnimations[customGraphicIds.endingAnimationId]
                                ?: endingAnimations[Res.string.value_default]!!
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val time = timerState.timer.elapsedTime.toTimeComponents()
                        SmoothFieldFadeAnimatedVisibility(time.days != 0L) {
                            TimeSelector(
                                time.days,
                                selectable = false,
                                label = stringResource(Res.string.days)
                            )
                        }
                        SmoothFieldFadeAnimatedVisibility(time.hours != 0L) {
                            TimeSelector(
                                time.hours,
                                selectable = false,
                                label = stringResource(Res.string.hours)
                            )
                        }

                        TimeSelector(
                            time.minutes,
                            selectable = false,
                            label = stringResource(Res.string.minutes)
                        )
                        TimeSelector(
                            time.seconds,
                            selectable = false,
                            label = stringResource(Res.string.seconds)
                        )
                    }
                }

                Spacer(Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(2f)
                ) {
                    SmoothFieldFadeAnimatedVisibility(visible = !timerState.ui.hide && !timerState.ui.lock) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RoundIconOutlinedSmall(
                                Res.drawable.property_1_eye_off,
                                stringResource(Res.string.hide_ui),
                                enabled = timerState.timer.elapsedTime != 0L
                            ) {
                                onTimerIntent(TimerFullScreenIntent.IconAppear)
                                onTimerIntent(TimerFullScreenIntent.Hide)
                            }
                            Spacer(Modifier.width(SizeXL))
                            RoundIconOutlinedSmall(
                                Res.drawable.property_1_stop,
                                stringResource(Res.string.stop),
                                enabled = timerState.timer.elapsedTime != 0L
                            ) {
                                onTimerIntent(TimerFullScreenIntent.Stop)
                                onNavigateBack()
                            }
                            Spacer(Modifier.width(SizeXL))
                            if (timerState.timer.isRunning)
                                RoundIconFilledMedium(
                                    Res.drawable.property_1_pause_circle,
                                    stringResource(Res.string.pause),
                                    enabled = timerState.timer.elapsedTime != 0L
                                ) {
                                    onTimerIntent(TimerFullScreenIntent.Pause)
                                }
                            else
                                RoundIconFilledMedium(
                                    Res.drawable.property_1_play,
                                    stringResource(Res.string.play),
                                    enabled = timerState.timer.elapsedTime != 0L
                                ) {
                                    onTimerIntent(TimerFullScreenIntent.Resume)
                                }
                            Spacer(Modifier.width(SizeXL))
                            RoundIconOutlinedSmall(
                                Res.drawable.property_1_lock_01,
                                stringResource(Res.string.lock),
                                enabled = timerState.timer.elapsedTime != 0L
                            ) {
                                onTimerIntent(TimerFullScreenIntent.IconAppear)
                                onTimerIntent(TimerFullScreenIntent.Lock)
                            }
                            Spacer(Modifier.width(SizeXL))
                            RoundIconOutlinedSmall(
                                Res.drawable.property_1_share_06,
                                stringResource(Res.string.share),
                                enabled = timerState.timer.elapsedTime != 0L
                            ) {
                                coroutineScope.launch {
                                    val shareText =
                                        getString(
                                            Res.string.my_timer_state,
                                            getString(timerState.timer.mode.value)
                                                .toLowerCase(LocalUtil.local),
                                            timerState.timer.elapsedTime.toTimeComponents()
                                                .toDisplayString()
                                        )

                                    ShareExtension.share(shareText)
                                }

                            }
                        }
                    }

                    SmoothFieldFadeAnimatedVisibility(visible = timerState.ui.hide && timerState.ui.iconAppear) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RoundIconOutlinedSmall(
                                Res.drawable.property_1_eye,
                                stringResource(Res.string.show_ui),
                                enabled = timerState.timer.elapsedTime != 0L
                            ) {
                                onTimerIntent(TimerFullScreenIntent.Hide)
                            }
                        }
                    }

                    SmoothFieldFadeAnimatedVisibility(visible = timerState.ui.lock && timerState.ui.iconAppear) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RoundIconOutlinedSmall(
                                Res.drawable.property_1_lock_unlocked_01,
                                stringResource(Res.string.show_ui),
                                enabled = timerState.timer.elapsedTime != 0L,
                                onLongPress3Second = {
                                    onTimerIntent(TimerFullScreenIntent.Lock)
                                }
                            ) {
                                ToastHandler.show(holdForSecondsString)
                                onTimerIntent(TimerFullScreenIntent.IconAppear)
                            }
                        }
                    }
                }

                Spacer(Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CaptionTextField(stringResource(Res.string.powered_by))
                    Icon(
                        painter = painterResource(Res.drawable.timezeer),
                        modifier = Modifier.height(SizeS),
                        contentDescription = stringResource(
                            Res.string.titleIcon
                        ),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@LightDarkPreviews
@Composable
fun TimerStartedPreview() {
    ThemedPreview {
        TimerStarted(
            timerState = FullScreenTimerState(
                timer = TimerState(
                    title = "Work",
                    mode = TimerMode.COUNTDOWN,
                    elapsedTime = 3661000L, // 1 hour, 1 minute, 1 second
                    isRunning = true
                ),
                ui = TimerUiState()
            ), onTimerIntent = {}, onNavigateBack = {})
    }
}


