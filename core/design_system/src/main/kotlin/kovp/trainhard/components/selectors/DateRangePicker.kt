package kovp.trainhard.components.selectors

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.button.TrainButton
import kovp.trainhard.core_domain.orZero
import kovp.trainhard.design_system.R
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

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
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.gym_card_date_range_title),
                        style = themeTypography.header2,
                        color = themeColors.lime,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = themeColors.black)
            )

        },
        floatingActionButton = {
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
        },
        containerColor = themeColors.black,
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .padding(16.dp)
                .background(themeColors.red)
        )
        DateRangePicker(
            modifier = Modifier
                .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                .padding(top = it.calculateTopPadding())
//                .padding(horizontal = 16.dp)
            ,
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
//            title = {
//
//            },
            title = null,
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

    BackHandler { onDismiss() }
}
