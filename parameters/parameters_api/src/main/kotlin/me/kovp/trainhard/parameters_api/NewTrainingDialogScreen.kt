package me.kovp.trainhard.parameters_api

import me.kovp.trainhard.navigation_api.Screen

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
