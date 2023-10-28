package kovp.trainhard.training_calendar_presentation

import java.time.LocalDate

sealed interface TrainingCalendarEvent {
    data class OnTrainingDayClick(val day: LocalDate) : TrainingCalendarEvent
}
