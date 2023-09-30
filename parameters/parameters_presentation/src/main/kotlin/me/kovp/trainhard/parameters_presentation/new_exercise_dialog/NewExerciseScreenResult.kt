package me.kovp.trainhard.parameters_presentation.new_exercise_dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface NewExerciseScreenResult : Parcelable {
    @Parcelize
    data class Success(
        val title: String,
        val muscleIds: List<String>,
        val screenAction: ScreenAction,
    ) : NewExerciseScreenResult {
        enum class ScreenAction {
            ADD,
            EDIT,
            ;
        }
    }

    @Parcelize
    data object Fail: NewExerciseScreenResult
}
