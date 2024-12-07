package kovp.trainhard.new_training_presentation.new_set_dialog

import kotlinx.serialization.Serializable

sealed class NewSetDialogResult {
    @Serializable
    data class Success(
        val id: Long,
        val setId: Long,
        val exerciseTitle: String,
        val weight: Float,
        val reps: Int,
        val resultAction: DialogAction,
    ) : NewSetDialogResult()

    @Serializable
    data object Error : NewSetDialogResult()

    enum class DialogAction {
        ADD_NEW,
        EDIT_CURRENT,
    }
}
