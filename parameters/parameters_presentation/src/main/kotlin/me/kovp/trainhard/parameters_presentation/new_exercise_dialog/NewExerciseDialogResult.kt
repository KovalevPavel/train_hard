package me.kovp.trainhard.parameters_presentation.new_exercise_dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface NewExerciseDialogResult: Parcelable {
    @Parcelize
    data class Success(
        val title: String,
        val muscleIds: List<String>,
        val dialogAction: DialogAction,
    ) : NewExerciseDialogResult {
        enum class DialogAction {
            ADD,
            EDIT,
            ;
        }
    }

    @Parcelize
    object Fail: NewExerciseDialogResult
}
