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
        val muscleGroups: ImmutableList<MuscleGroupVs>,
    ) : ExerciseParametersState

    data class MuscleGroupVs(
        val title: String,
        val muscles: ImmutableList<MuscleVs>,
    )

    data class MuscleVs(
        val id: String,
        val title: String,
        val isSelected: Boolean,
    )
}
