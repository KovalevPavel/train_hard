package kovp.trainhard.training_calendar_presentation

sealed interface TrainingCalendarEvent {
    data class OpenNewTrainingScreen(val timestamp: Long): TrainingCalendarEvent
}
