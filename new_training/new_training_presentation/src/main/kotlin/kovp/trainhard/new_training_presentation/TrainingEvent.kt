package kovp.trainhard.new_training_presentation

import kovp.trainhard.components.train_card.CompletedExerciseCardDto
import kovp.trainhard.new_training_api.NewSetDialogScreen
import kovp.trainhard.new_training_presentation.new_set_dialog.NewSetDialogResult

sealed interface TrainingEvent {
    data class AddOrEditSet(
        val data: NewSetDialogResult.Success,
    ) : TrainingEvent

    data class AddNewCompletedExercise(
        val data: NewSetDialogResult.Success,
    ) : TrainingEvent

    data object OnAddExerciseClick : TrainingEvent

    data class NavigateToSetDialog(
        val dialog: NewSetDialogScreen,
    ) : TrainingEvent

    data class OnRemoveSetClick(
        val setDto: CompletedExerciseCardDto,
        val setIndex: Int,
    ) : TrainingEvent
}
