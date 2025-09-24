package com.t2.timerzeerkmp.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.t2.timerzeerkmp.data.persistence.SettingsPrefsKeys
import com.t2.timerzeerkmp.presentation.main.theme.fontStyles
import com.tina.timerzeer.core.presentation.theme.SizeL
import com.tina.timerzeer.core.presentation.theme.SizeXL
import com.tina.timerzeer.core.presentation.theme.SizeXXL
import com.tina.timerzeer.core.presentation.theme.SizeXXXL
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultBottomSheet(
    title: StringResource,
    leadingIcon: DrawableResource,
    selected: StringResource,
    optionList: List<StringResource>,
    settingsPrefsKeys: SettingsPrefsKeys? = null,
    onDismiss: () -> Unit,
    onItemSelected: (StringResource) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = SizeXXL, topEnd = SizeXXL),
        containerColor = colorScheme.background,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HeadlineSmallTextField(
                textId = title,
                leadingIcon = leadingIcon
            )
            Spacer(Modifier.height(SizeXL))

            optionList.onEachIndexed { index, item ->
                if (index != 0)
                    BottomSheetDivider()
                if (item == selected) {
                    DefaultStyleOption(
                        item,
                        color = colorScheme.secondary,
                        settingsPrefsKeys = settingsPrefsKeys,
                        onStyleSelected = onItemSelected
                    )
                } else {
                    DefaultStyleOption(item, settingsPrefsKeys, onStyleSelected = onItemSelected)
                }
            }

            Spacer(Modifier.height(SizeXL))
        }
    }
}

@Composable
private fun BottomSheetDivider() {
    Spacer(
        Modifier
            .height(1.dp)
            .fillMaxWidth()
            .padding(horizontal = SizeXXXL)
            .background(colorScheme.tertiary)
    )
}

@Composable
private fun DefaultStyleOption(
    nameId: StringResource,
    settingsPrefsKeys: SettingsPrefsKeys?,
    color: Color = colorScheme.onPrimary,
    onStyleSelected: (nameId: StringResource) -> Unit = {}
) {
    val fontFamily =
        if (settingsPrefsKeys == SettingsPrefsKeys.FONT_STYLE) fontStyles()[nameId] else null
    val style = fontFamily?.let {
        typography.bodyMedium.copy(fontFamily = fontFamily)
    } ?: typography.bodyMedium

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onStyleSelected(nameId) }
            .padding(vertical = SizeL),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(nameId),
            style = style,
            color = color
        )
    }
}
