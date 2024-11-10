package kovp.trainhard.new_training_presentation.select_new_exercise_type

import kovp.trainhard.new_training_presentation.NewSetDialogScreen

sealed interface SelectExerciseEvent {
    data class NavigateToNewSetDialog(
        val data: NewSetDialogScreen,
    ) : SelectExerciseEvent
}
