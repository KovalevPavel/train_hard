package kovp.trainhard.new_training_presentation

import kovp.trainhard.components.train_card.CompletedExerciseCardDto
import kovp.trainhard.new_training_api.NewSetDialogScreen
import kovp.trainhard.new_training_presentation.new_set_dialog.NewSetDialogResult

sealed interface TrainingAction {
    data class AddOrEditSet(
        val data: NewSetDialogResult.Success,
    ) : TrainingAction

    data class AddNewCompletedExercise(
        val data: NewSetDialogResult.Success,
    ) : TrainingAction

    data object OnAddExerciseClick : TrainingAction

    data class NavigateToSetDialog(
        val dialog: NewSetDialogScreen,
    ) : TrainingAction

    data class OnRemoveSetClick(
        val setDto: CompletedExerciseCardDto,
        val setIndex: Int,
    ) : TrainingAction
}
