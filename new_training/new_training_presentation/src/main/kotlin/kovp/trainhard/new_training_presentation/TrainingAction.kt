package kovp.trainhard.new_training_presentation

import kovp.trainhard.components.train_card.CompletedExerciseCardDto
import kovp.trainhard.new_training_presentation.new_set_dialog.EditSetDialogResult
import kovp.trainhard.new_training_presentation.new_set_dialog.EditSetDialogVs
import trainhard.kovp.core.RequestAction

sealed interface TrainingAction {
    data class AddOrEditSet(
        val data: EditSetDialogResult.Success,
        val action: RequestAction,
    ) : TrainingAction

    data class AddNewCompletedExercise(
        val data: EditSetDialogResult.Success,
    ) : TrainingAction

    data object OnAddExerciseClick : TrainingAction

    data class NavigateToSetDialog(
        val dialog: EditSetDialogVs,
    ) : TrainingAction

    data class OnRemoveSetClick(
        val setDto: CompletedExerciseCardDto,
        val setIndex: Int,
    ) : TrainingAction
}
