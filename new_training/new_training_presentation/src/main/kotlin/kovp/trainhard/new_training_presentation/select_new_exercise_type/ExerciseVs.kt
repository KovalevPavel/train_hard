package kovp.trainhard.new_training_presentation.select_new_exercise_type

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kovp.trainhard.core_domain.Muscle

@Immutable
data class ExerciseVs(
    val title: String,
    val muscles: ImmutableList<Muscle>,
)
