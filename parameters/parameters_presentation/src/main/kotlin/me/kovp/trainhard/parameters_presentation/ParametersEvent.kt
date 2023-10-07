package me.kovp.trainhard.parameters_presentation

import me.kovp.trainhard.components.exercise_type.ExerciseCardDto

sealed interface ParametersEvent {
    data class ShowConfirmDeleteDialog(
        val exercise: ExerciseCardDto,
    ) : ParametersEvent
}
