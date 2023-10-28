package kovp.trainhard.new_training_presentation.select_new_exercise_type

import kovp.trainhard.new_training_api.NewSetDialogScreen

sealed interface SelectExerciseAction {
    data object Empty : SelectExerciseAction
    data class NavigateToNewSetDialog(
        val data: NewSetDialogScreen,
    ) : SelectExerciseAction
}
