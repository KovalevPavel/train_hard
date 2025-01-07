package kovp.trainhard.components.exercise_type

import kovp.trainhard.core_domain.MuscleGroup

data class ExerciseCardVs(
    val title: String,
    val muscles: List<MuscleVs>,
) {
    data class MuscleVs(
        val muscleId: String,
        val muscleGroup: MuscleGroup,
    ) {
        val id get() = "${muscleGroup.groupId}_$muscleId"
    }
}
