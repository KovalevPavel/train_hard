package kovp.trainhard.new_training_presentation.screen

import kotlinx.collections.immutable.ImmutableList
import kovp.trainhard.components.train_card.CompletedExerciseCardVs

sealed interface TrainingScreenState {
    data object Loading : TrainingScreenState

    data class Data(
        val items: ImmutableList<CompletedExerciseCardVs>,
    ) : TrainingScreenState
}
