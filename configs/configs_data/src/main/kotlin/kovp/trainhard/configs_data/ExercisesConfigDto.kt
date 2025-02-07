package kovp.trainhard.configs_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExercisesConfigDto(
    @SerialName("muscleGroups")
    val muscleGroups: Map<String, String>?,
    @SerialName("muscles")
    val muscles: Map<String, Map<String, String?>?>,
    @SerialName("defaultExercises")
    val defaultExercises: List<DefaultExerciseDto>
) {
    @Serializable
    data class DefaultExerciseDto(
        @SerialName("title")
        val title: String?,
        @SerialName("muscles")
        val muscles: List<String>?,
    )
}
