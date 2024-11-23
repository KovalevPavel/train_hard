package kovp.trainhard.parameters_presentation.exercise_parameters.presentation

sealed interface ExerciseParametersAction {
    data object OnActionClick : ExerciseParametersAction
    data object OnBackClick : ExerciseParametersAction

    @JvmInline
    value class OnNameChanged(val name: String) : ExerciseParametersAction

    data class OnMuscleAction(
        val muscleId: String,
        val isSelected: Boolean,
    ) : ExerciseParametersAction

    @JvmInline
    value class OnDialogPositiveClick(val dialogId: String) : ExerciseParametersAction
}
