package com.t2.timerzeerkmp.presentation.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.t2.timerzeerkmp.domain.util.currentTimeMillis
import com.tina.timerzeer.core.presentation.theme.SizeM
import com.tina.timerzeer.core.presentation.theme.SizeXL
import com.tina.timerzeer.core.presentation.theme.SizeXXL
import org.jetbrains.compose.resources.stringResource
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.cancel
import timerzeerkmp.composeapp.generated.resources.confirm
import timerzeerkmp.composeapp.generated.resources.property_1_calendar
import timerzeerkmp.composeapp.generated.resources.set_date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledDatePicker(onDateSelected: (Long) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTimeMillis(),
        selectableDates = object : SelectableDates {
            //todo
//            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
//
//                return utcTimeMillis >= currentTimeMillis().startOfDayInMillis()
//            }
//
//            override fun isSelectableYear(year: Int): Boolean {
//                return year >= Calendar.getInstance().get(Calendar.YEAR)
//            }
        })

    val datePickerColors = DatePickerDefaults.colors(
        containerColor = colorScheme.background,
        headlineContentColor = colorScheme.primary,
        weekdayContentColor = colorScheme.onSecondary,
        subheadContentColor = colorScheme.onSecondary,
        yearContentColor = colorScheme.onPrimary,
        currentYearContentColor = colorScheme.primary,
        selectedYearContentColor = colorScheme.onPrimary,
        selectedYearContainerColor = colorScheme.primary,
        dayContentColor = colorScheme.onSecondary,
        disabledDayContentColor = colorScheme.onSecondary,
        selectedDayContentColor = colorScheme.onPrimary,
        selectedDayContainerColor = colorScheme.primary,
        todayContentColor = colorScheme.primary,
        todayDateBorderColor = colorScheme.secondary,
    )
    DatePickerDialog(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(SizeXXL),
        colors = datePickerColors,
        confirmButton = {
            Text(
                stringResource(Res.string.confirm), Modifier
                    .padding(SizeM)
                    .clickable {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(it)
                        }
                        onDismiss()
                    })
        },
        dismissButton = {
            Text(
                text = stringResource(Res.string.cancel), modifier = Modifier
                    .padding(SizeM)
                    .clickable {
                        onDismiss()
                    })
        },
    ) {
        DatePicker(
            colors = datePickerColors,
            state = datePickerState,
            title = null,
            headline = {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    HeadlineSmallTextField(
                        textId = Res.string.set_date,
                        leadingIcon = Res.drawable.property_1_calendar,
                        modifier = Modifier.padding(vertical = SizeXL)
                    )
                }
            },
            showModeToggle = false,
            modifier = Modifier.padding(0.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@LightDarkPreviews
@Composable
fun DatePickerDockedPreview() {
    ThemedPreview {
        StyledDatePicker({}, {})
    }
}