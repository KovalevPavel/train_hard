package kovp.trainhard.core_dialogs.message_dialog

import kovp.trainhard.core_dialogs.DialogState

data class MessageDialogState(
    override val dialogId: String,
    val title: String = "",
    val message: String = "",
    val positiveAction: DialogState.Action,
    val negativeAction: DialogState.Action? = null,
    override val payload: Any? = null,
) : DialogState
