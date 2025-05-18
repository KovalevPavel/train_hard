package kovp.trainhard.home_presentation.gym_card_dates

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.json.Json
import kovp.trainhard.components.selectors.DateRangeSelectorState
import kovp.trainhard.components.selectors.ShowDatePickerDialog
import kovp.trainhard.home_presentation.navigation.SelectGymDatesScreen

@Composable
fun GymCardDatesDialog(
    initDateRangeState: DateRangeSelectorState,
    navController: NavController,
) {
    ShowDatePickerDialog(
        startTimestamp = initDateRangeState.startTimestamp,
        endTimestamp = initDateRangeState.endTimestamp,
        onApplyDateRange = { start, end ->
            val string = Json.encodeToString(
                serializer = DateRangeSelectorState.serializer(),
                value = DateRangeSelectorState(startTimestamp = start, endTimestamp = end),
            )
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    key = SelectGymDatesScreen.DATE_RANGE_KEY,
                    value = string,
                )

            navController.popBackStack()
        },
        onDismiss = {
            navController.popBackStack()
        },
    )
}
