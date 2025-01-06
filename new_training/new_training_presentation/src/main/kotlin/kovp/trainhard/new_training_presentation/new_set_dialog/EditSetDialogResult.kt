package kovp.trainhard.new_training_presentation.new_set_dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class EditSetDialogResult : Parcelable {
    @Parcelize
    data class Success(
        val id: Long,
        val setId: Long,
        val exerciseTitle: String,
        val weight: Float,
        val reps: Int,
    ) : EditSetDialogResult()
}
