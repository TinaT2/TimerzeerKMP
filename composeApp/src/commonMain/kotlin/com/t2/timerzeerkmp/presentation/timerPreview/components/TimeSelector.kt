package com.t2.timerzeerkmp.presentation.timerPreview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.t2.timerzeerkmp.presentation.main.components.LightDarkPreviews
import com.t2.timerzeerkmp.presentation.main.components.ThemedPreview
import com.t2.timerzeerkmp.presentation.main.theme.LocalCustomColors
import com.tina.timerzeer.core.presentation.theme.SizeM
import com.tina.timerzeer.core.presentation.theme.SizeS
import com.tina.timerzeer.core.presentation.theme.SizeXXXL
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.property_1_chevron_down
import timerzeerkmp.composeapp.generated.resources.property_1_chevron_up

@Composable
fun TimeSelector(
    value: Long,
    label: String,
    selectable: Boolean,
    onIncrease: () -> Unit = {},
    onDecrease: () -> Unit = {},
) {
    val customColors = LocalCustomColors.current
    val space by animateDpAsState(
        targetValue = if (!selectable) SizeXXXL else 0.dp,
        animationSpec = tween(durationMillis = 300),
        label = "paddingAnimation"
    )

    val spaceBottom by animateDpAsState(
        targetValue = if (selectable) 48.dp else 0.dp,
        animationSpec = tween(durationMillis = 2000),
        label = "paddingAnimation"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            ThemedArrowIcon(Res.drawable.property_1_chevron_up, visible = selectable, onIncrease)
            Spacer(modifier = Modifier.height(space))
        }


        Box(
            modifier = Modifier
                .padding(vertical = SizeS)
                .widthIn(min = 90.dp)
                .border(1.dp, customColors.border, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value.toString().padStart(2, '0'),
                color = colorScheme.onPrimary,
                style = typography.headlineLarge,
                modifier = Modifier.padding(horizontal = SizeM, vertical = SizeS)
            )
        }
        Box {
            ThemedArrowIcon(
                Res.drawable.property_1_chevron_down,
                visible = selectable
            ) { onDecrease() }
            Spacer(modifier = Modifier.height(spaceBottom))
        }

        Text(
            text = label,
            modifier = Modifier.padding(top = SizeS),
            color = customColors.textColorDisabled,
            style = typography.labelLarge
        )
        Spacer(modifier = Modifier.height(space))
    }
}

@Composable
private fun ThemedArrowIcon(
    imageVector: DrawableResource,
    visible: Boolean,
    onIncrease: () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) + slideInVertically(
            animationSpec = tween(durationMillis = 500)
        ) { it / 2 },
        exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutVertically(
            animationSpec = tween(durationMillis = 500)
        ) { it / 2 }
    ) {
        IconButton(
            onClick = onIncrease,
            modifier = Modifier
                .background(colorScheme.primary, shape = RoundedCornerShape(50))
        ) {
            Icon(
                painterResource(imageVector),
                contentDescription = "Increase",
                tint = colorScheme.surface
            )
        }
    }
}

@LightDarkPreviews
@Composable
fun TimeSelectorPreview() {
    ThemedPreview {
        TimeSelector(
            value = 10,
            label = "Minutes",
            selectable = false,
            onIncrease = {},
            onDecrease = {}
        )
    }
}

@LightDarkPreviews
@Composable
fun TimeSelectorSelectablePreview() {
    ThemedPreview {
        TimeSelector(
            value = 10,
            label = "Minutes",
            selectable = true,
            onIncrease = {},
            onDecrease = {}
        )
    }
}

