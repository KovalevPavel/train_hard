package kovp.trainhard.components.selectors

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.TopBar
import kovp.trainhard.components.button.TrainButton
import kovp.trainhard.core_domain.orZero
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.jetbrains.compose.resources.stringResource
import trainhard.design_system.generated.resources.Res
import trainhard.design_system.generated.resources.action_ok
import trainhard.design_system.generated.resources.gym_card_date_range_title

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

    Scaffold(
        containerColor = themeColors.black,
        topBar = {
            TopBar(
                header = null,
                onBackClick = onDismiss,
                navigationBackIcon = {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        tint = themeColors.white,
                        contentDescription = null,
                    )
                }
            )
        },
        floatingActionButton = {
            TrainButton(
                label = stringResource(resource = Res.string.action_ok),
                isEnabled = datePickerState.selectedStartDateMillis != null &&
                        datePickerState.selectedEndDateMillis != null,
            ) {
                onApplyDateRange(
                    datePickerState.selectedStartDateMillis.orZero(),
                    datePickerState.selectedEndDateMillis.orZero(),
                )
            }
        },
    ) { paddings ->
        DateRangePicker(
            modifier = Modifier.padding(top = paddings.calculateTopPadding())
                .padding(horizontal = 16.dp),
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
                    text = stringResource(resource = Res.string.gym_card_date_range_title),
                    style = themeTypography.header2,
                    color = themeColors.lime,
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
    }

    BackNavigationHandler { onDismiss() }
}
