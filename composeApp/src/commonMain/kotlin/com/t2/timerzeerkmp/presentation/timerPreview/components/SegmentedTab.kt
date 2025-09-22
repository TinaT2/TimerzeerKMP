package com.t2.timerzeerkmp.presentation.timerPreview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.t2.timerzeerkmp.domain.timer.TimerMode
import com.t2.timerzeerkmp.presentation.main.components.LightDarkPreviews
import com.t2.timerzeerkmp.presentation.main.theme.LocalCustomColors
import com.tina.timerzeer.core.presentation.theme.RoundedCornerShapeNumber
import com.tina.timerzeer.core.presentation.theme.SizeXS
import com.tina.timerzeer.core.presentation.theme.SizeXXS
import com.t2.timerzeerkmp.presentation.main.theme.TimerzeerTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.property_1_clock_fast_forward
import timerzeerkmp.composeapp.generated.resources.property_1_clock_stopwatch

@Composable
fun SegmentedTab(
    tabList: List<Pair<TimerMode, DrawableResource>>,
    selected: Int,
    onSelect: (Int) -> Unit
) {
    val customColors = LocalCustomColors.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                customColors.border,
                shape = RoundedCornerShape(RoundedCornerShapeNumber)
            )
            .padding(SizeXS),
        horizontalArrangement = Arrangement.Center
    ) {
        tabList.forEachIndexed { index, tab ->
            val isSelected = selected == index
            val bgColor = if (isSelected) colorScheme.primary else Color.Transparent
            val textColor = if (isSelected) colorScheme.surface else customColors.textColorDisabled

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(RoundedCornerShapeNumber))
                    .clickable { onSelect(index) }
                    .background(bgColor)
                    .padding(vertical = SizeXS),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(tab.second),
                    contentDescription = stringResource(tab.first.value),
                    tint = textColor
                )
                Spacer(Modifier.width(SizeXXS))
                Text(
                    text = stringResource(tab.first.value),
                    color = textColor,
                    style = typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun SegmentedTabPreview() {
    TimerzeerTheme {
        SegmentedTab(
            tabList = listOf(
                (TimerMode.STOPWATCH to Res.drawable.property_1_clock_stopwatch),
                (TimerMode.COUNTDOWN to Res.drawable.property_1_clock_fast_forward),
            ), selected = 0, onSelect = {})
    }
}

@Preview
@Composable
fun SegmentedTabCustomizedBgPreview() {
    TimerzeerTheme {
        SegmentedTab(
            tabList = listOf(
                (TimerMode.STOPWATCH to Res.drawable.property_1_clock_stopwatch),
                (TimerMode.COUNTDOWN to Res.drawable.property_1_clock_fast_forward),
            ), selected = 0, onSelect = {})
    }
}

@LightDarkPreviews
@Composable
fun SegmentedTabNightPreview() {
    TimerzeerTheme {
        SegmentedTab(
            tabList = listOf(
                (TimerMode.STOPWATCH to Res.drawable.property_1_clock_stopwatch),
                (TimerMode.COUNTDOWN to Res.drawable.property_1_clock_fast_forward)
            ), selected = 0, onSelect = {})
    }
}

