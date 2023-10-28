package kovp.trainhard.parameters_presentation.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseScreenArgument(
    val title: String?,
    val muscleIds: List<String>?,
) : Parcelable
