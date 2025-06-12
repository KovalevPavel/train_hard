package kovp.trainhard.components.train_card

import kotlinx.serialization.Serializable

@Serializable
data class CompletedExerciseCardDto(
    val setId: Long,
    val timestamp: Long,
    val exerciseTitle: String,
    val sets: List<Pair<Float, Int>>,
    val muscles: List<String>,
)
