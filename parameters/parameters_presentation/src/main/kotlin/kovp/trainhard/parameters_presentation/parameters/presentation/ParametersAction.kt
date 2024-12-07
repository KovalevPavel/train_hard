package kovp.trainhard.parameters_presentation.parameters.presentation

import kovp.trainhard.components.exercise_type.ExerciseCardVs
import kovp.trainhard.core_dialogs.DialogState

sealed interface ParametersAction {
    data class OnDeleteExerciseClicked(
        val exercise: ExerciseCardVs,
    ) : ParametersAction

    data class OnExerciseClick(
        val exercise: ExerciseCardVs,
    ) : ParametersAction

    data object OnAddButtonClick : ParametersAction

    data class OnDialogPositiveClick(
        val state: DialogState?,
    ) : ParametersAction
}
