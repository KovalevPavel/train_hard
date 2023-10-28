package kovp.trainhard.core_domain

abstract class Muscle {
    abstract val muscleGroup: MuscleGroup
    abstract val muscleId: String
    val id get() = "${muscleGroup.groupId}_$muscleId"
}
