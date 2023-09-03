package me.kovp.trainhard.new_training_presentation.select_new_exercise_type

import me.kovp.trainhard.database_api.models.Exercise

data class SelectExerciseScreenState(
    val items: List<Exercise>,
    val isLoading: Boolean,
) {
    companion object {
        val init = SelectExerciseScreenState(
            items = emptyList(),
            isLoading = true,
        )
    }
}
