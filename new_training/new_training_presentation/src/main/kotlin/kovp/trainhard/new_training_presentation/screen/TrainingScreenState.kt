package kovp.trainhard.new_training_presentation.screen

import kotlinx.collections.immutable.ImmutableList
import kovp.trainhard.components.train_card.CompletedExerciseCardDto

sealed interface TrainingScreenState {
    data class Data(
        val items: ImmutableList<CompletedExerciseCardDto>,
    ) : TrainingScreenState

    data object Loading : TrainingScreenState
}
