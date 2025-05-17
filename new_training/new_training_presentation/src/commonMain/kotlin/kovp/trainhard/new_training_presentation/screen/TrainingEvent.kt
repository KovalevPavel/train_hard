package kovp.trainhard.new_training_presentation.screen

import kovp.trainhard.new_training_presentation.edit_set_dialog.EditSetDialogVs

sealed interface TrainingEvent {
    data object NavigateToSelectExerciseType : TrainingEvent
    data class NavigateToEditSetDialog(
        val data: EditSetDialogVs,
    ) : TrainingEvent
}
