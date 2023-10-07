package me.kovp.trainhard.new_training_presentation

import me.kovp.trainhard.new_training_api.NewSetDialogScreen

sealed interface TrainingAction {
    data object Empty : TrainingAction
    data object NavigateToSelectExerciseType : TrainingAction
    data class NavigateToEditSetDialog(
        val data: NewSetDialogScreen,
    ) : TrainingAction
}
