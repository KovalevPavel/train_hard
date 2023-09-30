package me.kovp.trainhard.parameters_presentation

sealed interface ParametersAction {
    data object Empty : ParametersAction
    data object ShowDeleteConfirmationDialog : ParametersAction

    data class ShowItemIsAlreadyExistedDialog(
        val title: String,
    ) : ParametersAction
}
