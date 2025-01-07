package kovp.trainhard.configs_core

import kovp.trainhard.core_domain.Muscle

class ExercisesConfig(
    val defaultExercises: List<DefaultExercise>
) {
    data class DefaultExercise(
        val title: String,
        val muscles: List<Muscle>,
    )
}
