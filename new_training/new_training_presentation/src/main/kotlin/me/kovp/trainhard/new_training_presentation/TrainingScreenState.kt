package me.kovp.trainhard.new_training_presentation

import me.kovp.trainhard.components.train_card.CompletedExerciseCardDto

data class TrainingScreenState(
    val items: List<CompletedExerciseCardDto>,
    val isLoading: Boolean,
) {
    companion object {
        val init = TrainingScreenState(
            items = emptyList(),
            isLoading = true,
        )
    }
}
