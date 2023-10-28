package kovp.trainhard.database_api.models

import kovp.trainhard.core_domain.Muscle

data class Exercise(
    val title: String,
    val muscles: List<Muscle>,
)
