package me.kovp.trainhard.new_training_api

import me.kovp.trainhard.navigation_api.Screen

data class NewSetDialogScreen(
    val id: Long = 0,
    val setId: Long = 0,
    val exerciseTitle: String,
    val initWeight: Float = 0f,
    val initReps: Int = 0,
    val requestAction: RequestAction,
) : Screen {
    enum class RequestAction {
        ADD,
        EDIT,
        ;
    }
}
