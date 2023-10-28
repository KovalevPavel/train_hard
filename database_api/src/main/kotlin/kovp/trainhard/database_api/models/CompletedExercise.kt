package kovp.trainhard.database_api.models

typealias Sets = List<Pair<Float, Int>>

data class CompletedExercise(
    val id: Long,
    val exercise: Exercise,
    val sets: Sets,
    val dayTimestamp: Long,
)
