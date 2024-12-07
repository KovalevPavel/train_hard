package kovp.trainhard.parameters_presentation.parameters.presentation

import kotlinx.collections.immutable.ImmutableList
import kovp.trainhard.components.exercise_type.ExerciseCardVs

sealed interface ParametersScreenState {
    data class Data(
        val items: ImmutableList<ExerciseCardVs>,
    ) : ParametersScreenState

    data object Loading : ParametersScreenState
}
