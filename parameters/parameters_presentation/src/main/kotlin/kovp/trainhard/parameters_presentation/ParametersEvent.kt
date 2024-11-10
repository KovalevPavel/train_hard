package kovp.trainhard.parameters_presentation

import kovp.trainhard.components.exercise_type.ExerciseCardDto

sealed interface ParametersEvent {
    data class ShowDeleteConfirmationDialog(val exercise: ExerciseCardDto) : ParametersEvent

    data class ShowItemIsAlreadyExistedDialog(
        val title: String,
    ) : ParametersEvent

//    data class OpenNewExerciseScreen(
//        val data: NewExerciseDialogScreen,
//    ) : ParametersAction
}
