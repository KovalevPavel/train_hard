package kovp.trainhard.parameters_api

import kovp.trainhard.navigation.Screen

data class NewExerciseDialogScreen(
    val cardTitle: String? = null,
    val muscleIds: List<String>? = null,
    val requestAction: RequestAction,
) : Screen {
    enum class RequestAction {
        ADD,
        EDIT,
        ;
    }
}
