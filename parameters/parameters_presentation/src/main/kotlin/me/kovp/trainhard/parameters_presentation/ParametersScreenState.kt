package me.kovp.trainhard.parameters_presentation

import me.kovp.trainhard.components.exercise_type.ExerciseCardDto

data class ParametersScreenState(
    val items: List<ExerciseCardDto>,
    val isLoading: Boolean,
) {
    companion object {
        val init = ParametersScreenState(
            items = emptyList(),
            isLoading = true,
        )
    }
}
