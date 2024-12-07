package kovp.trainhard.new_training_presentation

import androidx.compose.runtime.Immutable
import kovp.trainhard.components.train_card.CompletedExerciseCardDto

@Immutable
sealed interface TrainingScreenState {
    data class Data(
        val items: List<CompletedExerciseCardDto>,
    ) : TrainingScreenState

    data object Loading : TrainingScreenState
}
