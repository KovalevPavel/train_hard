package me.kovp.trainhard.new_training_presentation

import me.kovp.trainhard.database_api.models.CompletedSet

data class TrainingScreenState(
    val items: List<SetItem>
) {
    data class SetItem(
        val payload: CompletedSet,
        val title: String,
        val muscleGroups: List<String>,
        val reps: List<String>,
    )

    companion object {
        val init = TrainingScreenState(
            items = emptyList(),
        )
    }
}
