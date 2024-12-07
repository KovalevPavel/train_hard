package kovp.trainhard.components.exercise_type

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kovp.trainhard.core_domain.MuscleGroup

@Parcelize
data class ExerciseCardDto(
    val title: String,
    val muscles: List<MuscleDto>,
) : Parcelable {
    @Parcelize
    data class MuscleDto(
        val muscleId: String,
        val muscleGroup: MuscleGroup,
    ) : Parcelable {
        val id get() = "${muscleGroup.groupId}_$muscleId"
    }
}
