package kovp.trainhard.home_presentation.gym_card_dates

import androidx.compose.runtime.Composable
import kovp.trainhard.components.selectors.DateRangeSelectorState
import kovp.trainhard.components.selectors.ShowDatePickerDialog

@Composable
fun GymCardDatesDialog(
    initDateRangeState: DateRangeSelectorState,
) {
    ShowDatePickerDialog(
        startTimestamp = initDateRangeState.startTimestamp,
        endTimestamp = initDateRangeState.endTimestamp,
        onApplyDateRange = { start, end ->
//            DateRangeSelectorState(start, end)
//                .let(resultBackNavigator::navigateBack)
        },
        onDismiss = {
//            resultBackNavigator.navigateBack()
        },
    )
}
