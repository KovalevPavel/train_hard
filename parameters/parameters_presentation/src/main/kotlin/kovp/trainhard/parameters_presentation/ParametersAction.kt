package kovp.trainhard.parameters_presentation

import kovp.trainhard.components.exercise_type.ExerciseCardDto
import kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult

sealed interface ParametersAction {
    data class AddOrEditExercise(
        val result: NewExerciseScreenResult.Success,
    ) : ParametersAction

    data class ShowConfirmDeleteDialog(
        val exercise: ExerciseCardDto,
    ) : ParametersAction

//    data class NavigateToNewExerciseScreen(
//        val data: NewExerciseDialogScreen,
//    ) : ParametersEvent

    data class RemoveExercise(
        val data: ExerciseCardDto,
    ) : ParametersAction
}
