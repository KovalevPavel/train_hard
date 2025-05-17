package kovp.trainhard.parameters_presentation.parameters.presentation

import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.parameters_presentation.navigation.ExerciseParametersArg

sealed interface ParametersEvent {
    data class ShowBottomSheetDialog(val dialogState: DialogState) : ParametersEvent
    data class NavigateToExerciseParams(val arg: ExerciseParametersArg) : ParametersEvent
}
