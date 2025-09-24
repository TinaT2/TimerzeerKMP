package com.t2.timerzeerkmp.presentation.timerPreview

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.t2.timerzeerkmp.data.mapper.toTimeComponents
import com.t2.timerzeerkmp.data.persistence.SettingsPrefsKeys
import com.t2.timerzeerkmp.domain.timer.TimerMode
import com.t2.timerzeerkmp.domain.util.currentTimeMillis
import com.t2.timerzeerkmp.presentation.main.components.DefaultBottomSheet
import com.t2.timerzeerkmp.presentation.main.components.LightDarkPreviews
import com.t2.timerzeerkmp.presentation.main.components.OutlinedPrimaryButton
import com.t2.timerzeerkmp.presentation.main.components.PrimaryButton
import com.t2.timerzeerkmp.presentation.main.components.SmoothFieldFadeAnimatedVisibility
import com.t2.timerzeerkmp.presentation.main.components.StyledDatePicker
import com.t2.timerzeerkmp.presentation.main.components.TextOptionButton
import com.t2.timerzeerkmp.presentation.main.components.ThemedPreview
import com.t2.timerzeerkmp.presentation.main.components.TimerInputField
import com.t2.timerzeerkmp.presentation.main.theme.LocalCustomColors
import com.t2.timerzeerkmp.presentation.main.theme.LocalCustomGraphicIds
import com.t2.timerzeerkmp.presentation.main.theme.backgrounds
import com.t2.timerzeerkmp.presentation.main.theme.endingAnimations
import com.t2.timerzeerkmp.presentation.main.theme.fontStyles
import com.t2.timerzeerkmp.presentation.timerPreview.components.SegmentedTab
import com.t2.timerzeerkmp.presentation.timerPreview.components.TimeSelector
import com.tina.timerzeer.core.presentation.theme.SizeS
import com.tina.timerzeer.core.presentation.theme.SizeXL
import com.tina.timerzeer.core.presentation.theme.SizeXS
import com.tina.timerzeer.core.presentation.theme.SizeXXXL
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.background_theme
import timerzeerkmp.composeapp.generated.resources.days
import timerzeerkmp.composeapp.generated.resources.ending_animation
import timerzeerkmp.composeapp.generated.resources.hours
import timerzeerkmp.composeapp.generated.resources.minutes
import timerzeerkmp.composeapp.generated.resources.property_1_calendar
import timerzeerkmp.composeapp.generated.resources.property_1_chevron_right
import timerzeerkmp.composeapp.generated.resources.property_1_clock_fast_forward
import timerzeerkmp.composeapp.generated.resources.property_1_clock_stopwatch
import timerzeerkmp.composeapp.generated.resources.property_1_flash
import timerzeerkmp.composeapp.generated.resources.property_1_image_02
import timerzeerkmp.composeapp.generated.resources.property_1_roller_brush
import timerzeerkmp.composeapp.generated.resources.seconds
import timerzeerkmp.composeapp.generated.resources.start
import timerzeerkmp.composeapp.generated.resources.timer_style
import timerzeerkmp.composeapp.generated.resources.timezeer
import timerzeerkmp.composeapp.generated.resources.titleIcon
import timerzeerkmp.composeapp.generated.resources.value_default

val DEFAULT_NAME = Res.string.value_default
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreenRoot(
    viewModel: TimerPreviewViewModel = koinViewModel(),
    onTimerStarted: () -> Unit = {}
) {
    val timerPreviewState by viewModel.timerPreviewState.collectAsStateWithLifecycle()
    var uiOverlayIntent: UiOverlayIntent by remember { mutableStateOf(UiOverlayIntent.None) }
    val customColors = LocalCustomColors.current
    val customGraphicIds = LocalCustomGraphicIds.current

    Box(modifier = Modifier.fillMaxSize()) {
        backgrounds()[customGraphicIds.backgroundId]?.invoke()

        Scaffold(
            modifier = Modifier
                .background(customColors.mainBackground)
                .padding(top = SizeXL),
            containerColor = customColors.mainBackground
        ) { paddingValues ->
            TimerScreen(
                paddingValues,
                timerPreviewState = timerPreviewState,
                onTimerStarted = {
                    viewModel.onUserAction(TimerPreviewIntent.OnTimerStarted)
                    onTimerStarted()
                },
                onUserActionIntent = { intent ->
                    viewModel.onUserAction(intent)
                },
                onStyleChange = { uiOverlayIntent = UiOverlayIntent.TimerStyle },
                onBackgroundThemeChange = { uiOverlayIntent = UiOverlayIntent.BackgroundTheme },
                onEndingAnimationChange = { uiOverlayIntent = UiOverlayIntent.EndingAnimation },
                onShowDatePicker = { uiOverlayIntent = UiOverlayIntent.DatePicker }
            )

            UIOverlays(
                uiOverlayIntent,
                { viewModel.onUserAction(it) },
                onDismiss = { uiOverlayIntent = UiOverlayIntent.None })
        }
    }
}

@Composable
private fun UIOverlays(
    uiOverlayIntent: UiOverlayIntent,
    onUserAction: (TimerPreviewIntent) -> Unit,
    onDismiss: () -> Unit
) {
    val customGraphicIds = LocalCustomGraphicIds.current
    when (uiOverlayIntent) {
        UiOverlayIntent.BackgroundTheme -> {
            DefaultBottomSheet(
                title = Res.string.background_theme,
                selected = customGraphicIds.backgroundId ?: DEFAULT_NAME,
                leadingIcon = Res.drawable.property_1_image_02,
                optionList = backgrounds().keys.toList(),
                onDismiss = {
                    onDismiss()
                }, onItemSelected = { backgroundId ->
                    onUserAction(TimerPreviewIntent.SetBackground(backgroundId))
                    onDismiss()
                })
        }

        UiOverlayIntent.DatePicker -> {
            StyledDatePicker(onDateSelected = {
                val now = currentTimeMillis()
                val diff = (it - now).coerceAtLeast(0)
                onUserAction(TimerPreviewIntent.SetDate(diff))
            }) {
                onDismiss()
            }
        }

        UiOverlayIntent.EndingAnimation -> {
            DefaultBottomSheet(
                title = Res.string.ending_animation,
                selected = customGraphicIds.endingAnimationId,
                leadingIcon = Res.drawable.property_1_flash,
                optionList = endingAnimations.keys.toList(),
                onDismiss = {
                    onDismiss()
                }, onItemSelected = { nameId ->
                    onUserAction(TimerPreviewIntent.SetEndingAnimation(nameId))
                    onDismiss()
                })
        }

        UiOverlayIntent.None -> {
            //Nothing
        }

        UiOverlayIntent.TimerStyle -> {
            DefaultBottomSheet(
                title = Res.string.timer_style,
                selected = customGraphicIds.fontId,
                leadingIcon = Res.drawable.property_1_roller_brush,
                optionList = fontStyles().keys.toList(),
                settingsPrefsKeys = SettingsPrefsKeys.FONT_STYLE,
                onDismiss = {
                    onDismiss()
                }, onItemSelected = {
                    onUserAction(TimerPreviewIntent.SetStyle(it))
                    onDismiss()
                })
        }
    }
}

@Composable
private fun TimerScreen(
    paddingValues: PaddingValues,
    onTimerStarted: () -> Unit,
    timerPreviewState: TimerPreviewState,
    onUserActionIntent: (TimerPreviewIntent) -> Unit,
    onStyleChange: () -> Unit = {},
    onBackgroundThemeChange: () -> Unit = {},
    onEndingAnimationChange: () -> Unit = {},
    onShowDatePicker: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val customGraphicIds = LocalCustomGraphicIds.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = SizeS)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(Res.drawable.timezeer),
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = stringResource(
                            Res.string.titleIcon
                        ),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(SizeXXXL))

                    SegmentedTab(
                        listOf(
                            (TimerMode.STOPWATCH to Res.drawable.property_1_clock_stopwatch),
                            (TimerMode.COUNTDOWN to Res.drawable.property_1_clock_fast_forward)
                        ), selected = timerPreviewState.mode.ordinal,
                        onSelect = {
                            onUserActionIntent(TimerPreviewIntent.OnModeChange(TimerMode.entries[it]))
                        })

                    Spacer(modifier = Modifier.height(SizeXXXL))

                    TimerInputField(
                        value = timerPreviewState.getTitle(),
                        error = timerPreviewState.errorMessage,
                        placeholder = stringResource(timerPreviewState.getPlaceHolder())
                    ) {
                        onUserActionIntent(timerPreviewState.getOnTitleChange(it))
                    }

                    Spacer(Modifier.height(SizeXL))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val time =
                            if (timerPreviewState.mode == TimerMode.STOPWATCH) 0L.toTimeComponents() else timerPreviewState.countDownInitTime.toTimeComponents()
                        SmoothFieldFadeAnimatedVisibility(time.days > 0) {
                            TimeSelector(
                                time.days,
                                selectable = timerPreviewState.mode == TimerMode.COUNTDOWN,
                                label = stringResource(Res.string.days),
                                onIncrease = { onUserActionIntent(TimerPreviewIntent.OnDayIncrease) },
                                onDecrease = { onUserActionIntent(TimerPreviewIntent.OnDayDecrease) },
                            )
                        }

                        TimeSelector(
                            time.hours,
                            selectable = timerPreviewState.mode == TimerMode.COUNTDOWN,
                            label = stringResource(Res.string.hours),
                            onIncrease = { onUserActionIntent(TimerPreviewIntent.OnHourIncrease) },
                            onDecrease = { onUserActionIntent(TimerPreviewIntent.OnHourDecrease) })
                        TimeSelector(
                            time.minutes,
                            selectable = timerPreviewState.mode == TimerMode.COUNTDOWN,
                            label = stringResource(Res.string.minutes),
                            onIncrease = { onUserActionIntent(TimerPreviewIntent.OnMinutesIncrease) },
                            onDecrease = { onUserActionIntent(TimerPreviewIntent.OnMinutesDecrease) })
                        TimeSelector(
                            time.seconds,
                            selectable = timerPreviewState.mode == TimerMode.COUNTDOWN,
                            label = stringResource(Res.string.seconds),
                            onIncrease = { onUserActionIntent(TimerPreviewIntent.OnSecondIncrease) },
                            onDecrease = { onUserActionIntent(TimerPreviewIntent.OnSecondDecrease) })
                    }

                    SmoothFieldFadeAnimatedVisibility(timerPreviewState.mode == TimerMode.COUNTDOWN) {
                        Column {
                            Spacer(Modifier.height(SizeXL))

                            OutlinedPrimaryButton(
                                text = "Set by date",
                                leadingIcon = Res.drawable.property_1_calendar
                            ) {
                                onShowDatePicker()
                            }
                        }
                    }

                    Spacer(Modifier.height(SizeXXXL))

                    TextOptionButton(
                        text = stringResource(customGraphicIds.fontId),
                        leadingIcon = Res.drawable.property_1_roller_brush,
                        trailingIcon = Res.drawable.property_1_chevron_right,
                        enabled = customGraphicIds.fontId != DEFAULT_NAME
                    ) {
                        onStyleChange()
                    }
                    Spacer(Modifier.height(SizeXS))
                    TextOptionButton(
                        text = stringResource(
                            customGraphicIds.backgroundId ?: DEFAULT_NAME
                        ),
                        leadingIcon = Res.drawable.property_1_image_02,
                        trailingIcon = Res.drawable.property_1_chevron_right,
                        enabled = customGraphicIds.backgroundId != DEFAULT_NAME && customGraphicIds.backgroundId != null
                    ) {
                        onBackgroundThemeChange()
                    }
                    Spacer(Modifier.height(SizeXS))

                    SmoothFieldFadeAnimatedVisibility(timerPreviewState.mode == TimerMode.COUNTDOWN) {
                        TextOptionButton(
                            text = stringResource(customGraphicIds.endingAnimationId),
                            leadingIcon = Res.drawable.property_1_flash,
                            trailingIcon = Res.drawable.property_1_chevron_right,
                            enabled = customGraphicIds.endingAnimationId != DEFAULT_NAME
                        ) {
                            onEndingAnimationChange()
                        }
                    }

                    Spacer(Modifier.height(100.dp))
                }
            }
        }
        PrimaryButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = stringResource(Res.string.start),
            enabled = if (timerPreviewState.mode == TimerMode.COUNTDOWN) timerPreviewState.countDownInitTime != 0L else true,
            onClick = { onTimerStarted() })
    }
}

@LightDarkPreviews
@Composable
private fun StopwatchScreenPreview() {
    ThemedPreview {
        TimerScreen(
            paddingValues = PaddingValues(),
            timerPreviewState = TimerPreviewState(
                stopwatchTitle = "Work Session",
                mode = TimerMode.STOPWATCH,
                countDownInitTime = 3661000L,
                errorMessage = null
            ),
            onUserActionIntent = {},
            onTimerStarted = {}
        )
    }
}

@LightDarkPreviews
@Composable
private fun CountdownScreenPreview() {
    ThemedPreview {
        TimerScreen(
            paddingValues = PaddingValues(),
            timerPreviewState = TimerPreviewState(
                stopwatchTitle = "Work Session",
                mode = TimerMode.COUNTDOWN,
                countDownInitTime = 3661000L,
                errorMessage = null
            ),
            onUserActionIntent = {},
            onTimerStarted = {}
        )
    }
}
