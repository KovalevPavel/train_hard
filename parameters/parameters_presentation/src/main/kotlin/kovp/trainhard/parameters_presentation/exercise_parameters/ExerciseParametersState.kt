package kovp.trainhard.parameters_presentation.exercise_parameters

import kotlinx.collections.immutable.ImmutableList

sealed interface ExerciseParametersState {
    data object Loading : ExerciseParametersState

    data class Content(
        val screenTitle: String,
        val action: String,
        val muscleName: String,
        val muscleIds: ImmutableList<String>,
    ) : ExerciseParametersState
}
