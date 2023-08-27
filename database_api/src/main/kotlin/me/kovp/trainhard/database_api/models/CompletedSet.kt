package me.kovp.trainhard.database_api.models

typealias Reps = List<Pair<Float, Int>>

data class CompletedSet(
    val date: String,
    val exercise: Exercise,
    val reps: Reps,
)
