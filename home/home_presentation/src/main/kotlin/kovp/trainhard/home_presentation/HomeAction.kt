package kovp.trainhard.home_presentation

sealed interface HomeAction {
    data object Empty : HomeAction
    data object OpenNewTrainingScreen : HomeAction
    data object OpenTrainingCalendar : HomeAction
    data class OpenDatePickerDialog(
        val startDate: Long?,
        val endDate: Long?,
    ) : HomeAction
}
