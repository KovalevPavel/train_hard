package kovp.trainhard.new_training_presentation.screen

import kovp.trainhard.components.train_card.CompletedExerciseCardVs
import kovp.trainhard.new_training_presentation.edit_set_dialog.EditSetDialogResult
import kovp.trainhard.new_training_presentation.edit_set_dialog.EditSetDialogVs

sealed interface TrainingAction {
    data class AddOrEditSet(
        val data: EditSetDialogResult.Success,
    ) : TrainingAction

    data class AddNewCompletedExercise(
        val data: EditSetDialogResult.Success,
    ) : TrainingAction

    data object OnAddExerciseClick : TrainingAction

    data class NavigateToSetDialog(
        val dialog: EditSetDialogVs,
    ) : TrainingAction

    data class OnRemoveSetClick(
        val setDto: CompletedExerciseCardVs,
        val setIndex: Int,
    ) : TrainingAction
}
