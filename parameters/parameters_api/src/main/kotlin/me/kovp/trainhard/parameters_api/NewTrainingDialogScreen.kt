package me.kovp.trainhard.parameters_api

import me.kovp.trainhard.navigation_api.Screen

data class NewExerciseDialogScreen(
    val requestAction: RequestAction,
) : Screen {
    enum class RequestAction {
        ADD,
        EDIT,
        ;
    }
}
