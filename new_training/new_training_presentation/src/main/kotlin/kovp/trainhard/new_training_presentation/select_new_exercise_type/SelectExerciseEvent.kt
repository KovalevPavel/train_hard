package kovp.trainhard.new_training_presentation.select_new_exercise_type

sealed interface SelectExerciseEvent {
    @JvmInline
    value class NavigateBack(val exerciseTitle: String? = null) : SelectExerciseEvent
}
