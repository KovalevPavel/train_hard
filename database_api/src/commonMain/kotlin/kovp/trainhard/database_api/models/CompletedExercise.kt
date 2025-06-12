package kovp.trainhard.database_api.models

typealias Attempts = List<Pair<Float, Int>>

data class CompletedExercise(
    val id: Long,
    val exercise: ExerciseVo,
    val attempts: Attempts,
    val dayTimestamp: Long,
)
