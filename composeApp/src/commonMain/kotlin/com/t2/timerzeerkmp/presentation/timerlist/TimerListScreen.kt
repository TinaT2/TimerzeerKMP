package com.t2.timerzeerkmp.presentation.timerlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.t2.timerzeerkmp.data.mapper.toDayMonthYearString
import com.t2.timerzeerkmp.domain.timer.TimerMode
import com.t2.timerzeerkmp.domain.timer.TimerPresentation
import com.t2.timerzeerkmp.presentation.main.components.ThemedPreview
import com.t2.timerzeerkmp.presentation.main.theme.ColorfulScaffold
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.arrow_back_24dp
import timerzeerkmp.composeapp.generated.resources.property_1_calendar
import timerzeerkmp.composeapp.generated.resources.property_1_clock_stopwatch
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerListScreen(
    state: TimerListState,
    onIntent: (TimerListIntent) -> Unit,
) {
    val textColor = MaterialTheme.colorScheme.primary
    ColorfulScaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "History",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = textColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onIntent(TimerListIntent.OnBack) }) {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_back_24dp),
                            contentDescription = "Back",
                            tint = textColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.timers) { timer ->
                HistoryItem(timer)
            }
        }
    }
}

@Composable
fun HistoryItem(timer: TimerPresentation) {
    val cardColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
    val textColor = MaterialTheme.colorScheme.primary

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = timer.title.takeIf { it.isNotBlank() } ?: "Untitled",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Text(
                    text = timer.startTime.toDayMonthYearString(),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = timer.duration.inWholeSeconds.seconds.toString(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = textColor
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = if (timer.mode == TimerMode.STOPWATCH)
                            painterResource(Res.drawable.property_1_clock_stopwatch)
                        else painterResource(
                            Res.drawable.property_1_calendar
                        ),
                        contentDescription = timer.mode.name,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = timer.mode.name.lowercase().replaceFirstChar { it.uppercase() },
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TimerListScreenPreview() {
    ThemedPreview {
        val timers = listOf(
            TimerPresentation(
                2,
                "Presentation Prep",
                TimerMode.COUNTDOWN,
                0L,
                5.toDuration(DurationUnit.SECONDS),
                false
            ),
            TimerPresentation(
                3,
                "Presentation Prep",
                TimerMode.COUNTDOWN,
                0L,
                10L.toDuration(DurationUnit.SECONDS),
                false
            ),
            TimerPresentation(
                4,
                "Presentation Prep",
                TimerMode.STOPWATCH,
                0L,
                7.toDuration(DurationUnit.SECONDS),
                false
            ),
            TimerPresentation(
                5,
                "Presentation Prep",
                TimerMode.COUNTDOWN,
                0L,
                15L.toDuration(DurationUnit.SECONDS),
                false
            ),
        )
        val state = TimerListState(timers = timers)
        TimerListScreen(
            state = state,
            onIntent = {}
        )
    }
}
