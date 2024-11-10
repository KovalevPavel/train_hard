package kovp.trainhard.new_training_presentation

import kotlinx.serialization.Serializable

@Serializable
data class NewSetDialogScreen(
    val id: Long = 0,
    val setId: Long = 0,
    val exerciseTitle: String,
    val initWeight: Float = 0f,
    val initReps: Int = 0,
    val requestAction: RequestAction,
) {
    enum class RequestAction {
        ADD,
        EDIT,
        ;
    }
}
