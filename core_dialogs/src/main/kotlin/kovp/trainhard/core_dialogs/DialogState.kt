package kovp.trainhard.core_dialogs

interface DialogState {
    val dialogId: String
    val payload: Any? get() = null

    data class Action(
        val action: String,
    )
}
