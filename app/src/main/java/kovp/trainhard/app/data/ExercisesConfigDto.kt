package kovp.trainhard.app.data

import kotlinx.serialization.Serializable

@Serializable
data class ExercisesConfigDto(
    val defaultExercises: List<DefaultExerciseDto>
) {
    @Serializable
    data class DefaultExerciseDto(
        val title: String,
        val muscles: List<String>,
    )
}
