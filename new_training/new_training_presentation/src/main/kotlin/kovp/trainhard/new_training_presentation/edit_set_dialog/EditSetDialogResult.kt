package kovp.trainhard.new_training_presentation.edit_set_dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class EditSetDialogResult : Parcelable {
    /**
     * @param id id упражнения в текущей тренировке
     * @param setId id редактируемого сета в _текущем_ упражнении
     * @param exerciseTitle название упражнения
     * @param weight вес в текущем подходе
     * @param reps выполненное количество повторений
     */
    @Parcelize
    data class Success(
        val id: Long,
        val setId: Long?,
        val exerciseTitle: String,
        val weight: Float,
        val reps: Int,
    ) : EditSetDialogResult()
}
