package kovp.trainhard.parameters_presentation

import kovp.trainhard.components.exercise_type.ExerciseCardDto
import kovp.trainhard.core_dialogs.DialogState

sealed interface ParametersAction {
    data class OnDeleteExerciseClicked(
        val exercise: ExerciseCardDto,
    ) : ParametersAction

    data class OnExerciseClick(
        val exercise: ExerciseCardDto,
    ) : ParametersAction

    data object OnAddButtonClick : ParametersAction

    data class OnDialogPositiveClick(
        val state: DialogState?,
    ) : ParametersAction
}
