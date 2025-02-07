package kovp.trainhard.core_domain

interface Muscle {
    val muscleGroup: MuscleGroup
    val muscleId: String
    val id get() = "${muscleGroup.groupId}_$muscleId"
}
