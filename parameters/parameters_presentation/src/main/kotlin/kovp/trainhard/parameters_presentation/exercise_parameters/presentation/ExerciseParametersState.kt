package kovp.trainhard.parameters_presentation.exercise_parameters.presentation

import kotlinx.collections.immutable.ImmutableList

sealed interface ExerciseParametersState {
    val screenTitle: String get() = ""
    val action: String get() = ""
    data object Loading : ExerciseParametersState

    data class Content(
        override val screenTitle: String,
        override val action: String,
        val muscleName: String,
        val muscleIds: ImmutableList<String>,
    ) : ExerciseParametersState
}
