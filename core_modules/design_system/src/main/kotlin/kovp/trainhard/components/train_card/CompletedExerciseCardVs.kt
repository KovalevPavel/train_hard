package kovp.trainhard.components.train_card

/**
 * Упражнение в тренировке
 * @param setId id упражнения
 * @param timestamp timestamp упражнения
 * @param exerciseTitle название упражнения
 * @param sets подходы в упражнении
 * @param muscles задействованные мышцы (локализованная строка)
 */
data class CompletedExerciseCardVs(
    val setId: Long,
    val timestamp: Long,
    val exerciseTitle: String,
    val sets: List<Pair<Float, Int>>,
    val muscles: String,
)
