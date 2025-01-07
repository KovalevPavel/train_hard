package kovp.trainhard.new_training_presentation.edit_set_dialog

import trainhard.kovp.core.RequestAction

/**
 * @param id id упражнения в текущей тренировке
 * @param setId id редактируемого сета в _текущем_ упражнении
 * @param exerciseTitle название упражнения
 * @param initWeight начальный вес, который будет предложен в диалоге
 * @param initReps начальное количество повторений, которое будет предложено в диалоге
 * @param requestAction действие, которое предполагается выполнить с результатом диалога
 */
data class EditSetDialogVs(
    val id: Long = 0,
    val setId: Long? = null,
    val exerciseTitle: String,
    val initWeight: Float = 0f,
    val initReps: Int = 0,
    val requestAction: RequestAction,
)
