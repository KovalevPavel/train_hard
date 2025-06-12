package kovp.trainhard.new_training_presentation.select_new_exercise_type

sealed interface SelectExerciseAction {
    data class OnExerciseClick(
        val data: ExerciseVs,
    ) : SelectExerciseAction
}
