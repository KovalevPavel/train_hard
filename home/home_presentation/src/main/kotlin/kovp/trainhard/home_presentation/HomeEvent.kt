package kovp.trainhard.home_presentation

sealed interface HomeEvent {
    data object OpenNewTrainingScreen : HomeEvent
    data object OpenTrainingCalendar : HomeEvent
    data class OpenDatePickerDialog(val startDate: Long?, val endDate: Long?) : HomeEvent
}
