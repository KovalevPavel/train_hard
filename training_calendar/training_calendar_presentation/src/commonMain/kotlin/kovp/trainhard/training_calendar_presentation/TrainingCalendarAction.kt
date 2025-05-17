package kovp.trainhard.training_calendar_presentation

import java.time.LocalDate

sealed interface TrainingCalendarAction {
    data class OnTrainingDayClick(val day: LocalDate) : TrainingCalendarAction
}
