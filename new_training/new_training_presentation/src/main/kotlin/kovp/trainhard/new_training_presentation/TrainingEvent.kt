package kovp.trainhard.new_training_presentation

import kovp.trainhard.new_training_api.NewSetDialogScreen

sealed interface TrainingEvent {
    data object Empty : TrainingEvent
    data object NavigateToSelectExerciseType : TrainingEvent
    data class NavigateToEditSetDialog(
        val data: NewSetDialogScreen,
    ) : TrainingEvent
}
