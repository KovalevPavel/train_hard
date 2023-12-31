package kovp.trainhard.parameters_presentation

import kovp.trainhard.components.exercise_type.ExerciseCardDto
import kovp.trainhard.parameters_api.NewExerciseDialogScreen

sealed interface ParametersAction {
    data object Empty : ParametersAction
    data class ShowDeleteConfirmationDialog(val exercise: ExerciseCardDto) : ParametersAction

    data class ShowItemIsAlreadyExistedDialog(
        val title: String,
    ) : ParametersAction

    data class OpenNewExerciseScreen(
        val data: NewExerciseDialogScreen,
    ) : ParametersAction
}
