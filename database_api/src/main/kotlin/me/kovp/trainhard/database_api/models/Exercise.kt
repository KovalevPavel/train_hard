package me.kovp.trainhard.database_api.models

data class Exercise(
    val title: String,
    val muscleGroups: List<MuscleGroup>,
)
