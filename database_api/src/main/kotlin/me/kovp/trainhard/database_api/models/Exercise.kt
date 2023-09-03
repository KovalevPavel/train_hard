package me.kovp.trainhard.database_api.models

import me.kovp.trainhard.core_domain.MuscleGroup

data class Exercise(
    val title: String,
    val muscleGroups: List<MuscleGroup>,
)
