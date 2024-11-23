package kovp.trainhard.database_api.models

import kovp.trainhard.core_domain.Muscle

data class ExerciseVo(
    val title: String,
    val muscles: List<Muscle>,
)
