package kovp.trainhard.new_training_presentation.select_new_exercise_type

import kotlin.jvm.JvmInline

sealed interface SelectExerciseEvent {
    @JvmInline
    value class NavigateBack(val exerciseTitle: String? = null) : SelectExerciseEvent
}
