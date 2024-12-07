package kovp.trainhard.parameters_presentation.exercise_parameters.presentation

import kovp.trainhard.core_dialogs.message_dialog.MessageDialogState

sealed interface ExerciseParametersEvent {
    data class ShowMessageDialog(val state: MessageDialogState) : ExerciseParametersEvent
    data object NavigateBack : ExerciseParametersEvent
}
