package kovp.trainhard.parameters_presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseParametersArg(
    val title: String,
    val muscleIds: List<String>,
) {
    companion object {
        val empty = ExerciseParametersArg(
            title = "",
            muscleIds = emptyList(),
        )
    }
}
