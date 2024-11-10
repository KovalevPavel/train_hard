package kovp.trainhard.home_presentation.gym_card_dates

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kovp.trainhard.components.selectors.DateRangeSelectorState
import kovp.trainhard.components.selectors.ShowDatePickerDialog

@Composable
fun GymCardDatesDialog(
    initDateRangeState: DateRangeSelectorState,
    navController: NavController,
) {
    ShowDatePickerDialog(
        startTimestamp = initDateRangeState.startTimestamp,
        endTimestamp = initDateRangeState.endTimestamp,
        onApplyDateRange = { start, end ->
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    key = SelectGymDatesScreen.DATE_RANGE_KEY,
                    value = DateRangeSelectorState(startTimestamp = start, endTimestamp = end),
                )

            navController.popBackStack()
        },
        onDismiss = {
            navController.popBackStack()
        },
    )
}
