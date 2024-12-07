package kovp.trainhard.database_api.models

typealias Sets = List<Pair<Float, Int>>

data class CompletedExercise(
    val id: Long,
    val exercise: ExerciseVo,
    val sets: Sets,
    val dayTimestamp: Long,
)
