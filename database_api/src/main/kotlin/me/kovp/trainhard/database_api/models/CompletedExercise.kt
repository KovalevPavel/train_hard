package me.kovp.trainhard.database_api.models

typealias Sets = List<Pair<Float, Int>>

data class CompletedExercise(
    val id: Long,
    val date: String,
    val exercise: Exercise,
    val sets: Sets,
)
