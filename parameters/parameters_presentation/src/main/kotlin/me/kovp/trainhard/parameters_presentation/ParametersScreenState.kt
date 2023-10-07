package me.kovp.trainhard.parameters_presentation

import me.kovp.trainhard.components.exercise_type.ExerciseCardDto

sealed interface ParametersScreenState {
    data class Data(
        val items: List<ExerciseCardDto>,
    ) : ParametersScreenState

    data object Loading : ParametersScreenState
}
