package kovp.trainhard.new_training_presentation.select_new_exercise_type

import kotlinx.collections.immutable.ImmutableList

sealed interface SelectExerciseScreenState {
    data class Data(
        val items: ImmutableList<ExerciseVs>,
    ) : SelectExerciseScreenState

    data object Loading : SelectExerciseScreenState
}
