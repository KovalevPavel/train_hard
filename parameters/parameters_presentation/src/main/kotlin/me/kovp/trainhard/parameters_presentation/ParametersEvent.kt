package me.kovp.trainhard.parameters_presentation

import me.kovp.trainhard.components.exercise_type.ExerciseCardDto
import me.kovp.trainhard.parameters_api.NewExerciseDialogScreen
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult

sealed interface ParametersEvent {
    data class AddOrEditExercise(
        val result: NewExerciseScreenResult.Success,
    ) : ParametersEvent

    data class ShowConfirmDeleteDialog(
        val exercise: ExerciseCardDto,
    ) : ParametersEvent

    data class NavigateToNewExerciseScreen(
        val data: NewExerciseDialogScreen,
    ) : ParametersEvent

    data class RemoveExercise(
        val data: ExerciseCardDto,
    ) : ParametersEvent
}
