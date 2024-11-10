package kovp.trainhard.new_training_presentation

sealed interface TrainingEvent {
    data object NavigateToSelectExerciseType : TrainingEvent
    data class NavigateToEditSetDialog(
        val data: NewSetDialogScreen,
    ) : TrainingEvent
}
