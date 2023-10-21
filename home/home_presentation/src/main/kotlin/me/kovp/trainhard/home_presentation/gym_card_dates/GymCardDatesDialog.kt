package me.kovp.trainhard.home_presentation.gym_card_dates

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import me.kovp.trainhard.components.selectors.DateRangeSelectorState
import me.kovp.trainhard.components.selectors.ShowDatePickerDialog
import me.kovp.trainhard.navigation_api.navigation_styles.SlideFromBottomTransition

@Destination(style = SlideFromBottomTransition::class)
@Composable
fun GymCardDatesDialog(
    initDateRangeState: DateRangeSelectorState,
    resultBackNavigator: ResultBackNavigator<DateRangeSelectorState>,
) {
    ShowDatePickerDialog(
        startTimestamp = initDateRangeState.startTimestamp,
        endTimestamp = initDateRangeState.endTimestamp,
        onApplyDateRange = { start, end ->
            DateRangeSelectorState(start, end)
                .let(resultBackNavigator::navigateBack)
        },
        onDismiss = {
            resultBackNavigator.navigateBack()
        },
    )
}
