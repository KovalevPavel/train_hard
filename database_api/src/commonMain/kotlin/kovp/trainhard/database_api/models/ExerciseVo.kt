package kovp.trainhard.database_api.models

import kovp.trainhard.configs_core.ExercisesConfig

data class ExerciseVo(
    val title: String,
    val muscles: List<ExercisesConfig.MuscleVo>,
)
