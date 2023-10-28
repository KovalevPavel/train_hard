package kovp.trainhard.new_training_presentation.select_new_exercise_type

import kovp.trainhard.database_api.models.Exercise

sealed interface SelectExerciseScreenState {
    data class Data(
        val items: List<Exercise>,
    ) : SelectExerciseScreenState

    data object Loading : SelectExerciseScreenState
}
