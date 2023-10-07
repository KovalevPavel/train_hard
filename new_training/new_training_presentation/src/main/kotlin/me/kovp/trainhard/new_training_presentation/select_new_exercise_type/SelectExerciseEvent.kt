package me.kovp.trainhard.new_training_presentation.select_new_exercise_type

import me.kovp.trainhard.database_api.models.Exercise

sealed interface SelectExerciseEvent {
    data class OnExerciseClick(
        val data: Exercise,
    ) : SelectExerciseEvent
}
