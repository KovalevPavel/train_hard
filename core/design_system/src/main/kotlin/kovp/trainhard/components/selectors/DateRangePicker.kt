package kovp.trainhard.components.selectors

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kovp.trainhard.design_system.R
import kovp.trainhard.components.button.TrainButton
import kovp.trainhard.core_domain.orZero
import kovp.trainhard.ui_theme.providers.themeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePickerDialog(
    startTimestamp: Long? = null,
    endTimestamp: Long? = null,
    onApplyDateRange: (startTimestamp: Long, endTimestamp: Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = startTimestamp,
        initialSelectedEndDateMillis = endTimestamp,
    )
    val datePickerFormatter = remember { DatePickerFormatter() }

    Column(
        modifier = Modifier
            .background(color = themeColors.black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.End,
    ) {
        DateRangePicker(
            modifier = Modifier.weight(1f),
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = themeColors.black,
                titleContentColor = themeColors.lime,
                headlineContentColor = themeColors.lime,
                weekdayContentColor = themeColors.white,
                subheadContentColor = themeColors.white,
                dayContentColor = themeColors.white,
                selectedDayContainerColor = themeColors.lime,
                todayDateBorderColor = themeColors.lime,
                selectedDayContentColor = themeColors.black,
                dayInSelectionRangeContainerColor = themeColors.lime.copy(alpha = 0.5f),
                todayContentColor = themeColors.lime,
                dayInSelectionRangeContentColor = themeColors.lime,
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.gym_card_date_range_title),
                )
            },
            showModeToggle = false,
            headline = {
                DateRangePickerDefaults.DateRangePickerHeadline(
                    selectedStartDateMillis = datePickerState.selectedStartDateMillis,
                    selectedEndDateMillis = datePickerState.selectedEndDateMillis,
                    displayMode = DisplayMode.Picker,
                    dateFormatter = datePickerFormatter,
                )
            },
        )
        TrainButton(
            label = stringResource(id = R.string.action_ok),
            isEnabled = datePickerState.selectedStartDateMillis != null &&
                    datePickerState.selectedEndDateMillis != null,
        ) {
            onApplyDateRange(
                datePickerState.selectedStartDateMillis.orZero(),
                datePickerState.selectedEndDateMillis.orZero(),
            )
        }
    }

    BackHandler { onDismiss() }
}
