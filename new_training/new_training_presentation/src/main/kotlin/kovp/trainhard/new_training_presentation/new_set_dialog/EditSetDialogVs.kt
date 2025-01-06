package kovp.trainhard.new_training_presentation.new_set_dialog

import trainhard.kovp.core.RequestAction

data class EditSetDialogVs(
    val id: Long = 0,
    val setId: Long = 0,
    val exerciseTitle: String,
    val initWeight: Float = 0f,
    val initReps: Int = 0,
    val requestAction: RequestAction,
)
