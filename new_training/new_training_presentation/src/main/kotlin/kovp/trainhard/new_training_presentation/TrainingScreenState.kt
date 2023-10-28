package kovp.trainhard.new_training_presentation

import kovp.trainhard.components.train_card.CompletedExerciseCardDto

sealed interface TrainingScreenState {
    data class Data(
        val items: List<CompletedExerciseCardDto>,
    ) : TrainingScreenState

    data object Loading : TrainingScreenState
}
