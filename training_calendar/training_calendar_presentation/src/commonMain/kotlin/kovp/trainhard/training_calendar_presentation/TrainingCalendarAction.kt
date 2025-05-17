package kovp.trainhard.training_calendar_presentation

import kotlinx.datetime.LocalDate

sealed interface TrainingCalendarAction {
    data class OnTrainingDayClick(val day: LocalDate) : TrainingCalendarAction
}
