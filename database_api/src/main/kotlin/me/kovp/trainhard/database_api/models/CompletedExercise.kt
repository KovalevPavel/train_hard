package me.kovp.trainhard.database_api.models

data class CompletedExercise(
    val id: Long,
    val exercise: Exercise,
    val reps: List<Pair<Float, Int>>,
)
