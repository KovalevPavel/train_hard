package kovp.trainhard.training_calendar_presentation

sealed interface TrainingCalendarAction {
    data object Empty : TrainingCalendarAction
    data class OpenNewTrainingScreen(val timestamp: Long): TrainingCalendarAction
}
