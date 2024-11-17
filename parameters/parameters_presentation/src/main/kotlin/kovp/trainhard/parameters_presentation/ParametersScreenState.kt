package kovp.trainhard.parameters_presentation

import kotlinx.collections.immutable.ImmutableList
import kovp.trainhard.components.exercise_type.ExerciseCardDto

sealed interface ParametersScreenState {
    data class Data(
        val items: ImmutableList<ExerciseCardDto>,
    ) : ParametersScreenState

    data object Loading : ParametersScreenState
}
