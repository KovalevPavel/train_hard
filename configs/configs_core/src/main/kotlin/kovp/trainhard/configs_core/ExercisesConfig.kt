package kovp.trainhard.configs_core

import kovp.trainhard.core_domain.MuscleGroup

class ExercisesConfig(
    val muscleGroups: Map<MuscleGroup, String>,
    val muscles: List<MuscleVo>,
    val defaultExercises: List<DefaultExercise>
) {
    fun getLocalizedString(fullId: String): String? {
        return muscles.getMuscleByFullId(fullId)?.localizedString
    }

    fun getMuscleGroup(id: String) = muscles.firstOrNull {
        it.id.equals(id, ignoreCase = true)
    }
        ?.muscleGroup

    data class MuscleVo(
        val id: String,
        val localizedString: String,
        val muscleGroup: MuscleGroup,
    )

    data class DefaultExercise(
        val title: String,
        val muscles: List<MuscleVo>,
    )
}

fun List<ExercisesConfig.MuscleVo>.getMuscleByFullId(fullId: String): ExercisesConfig. MuscleVo? {
    return this.firstOrNull {
        it.id.equals(fullId, ignoreCase = true)
    }
}
