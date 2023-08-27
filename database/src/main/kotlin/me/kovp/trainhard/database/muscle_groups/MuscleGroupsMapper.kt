package me.kovp.trainhard.database.muscle_groups

import me.kovp.trainhard.database.entities.MuscleGroupEntity
import me.kovp.trainhard.database_api.models.MuscleGroup

internal class MuscleGroupsMapper {
    fun mapMuscleGroupToDomainById(
        id: String,
    ): MuscleGroup? {
        val muscleGroupId = id.split(MUSCLE_GROUP_DELIMITER)
            .firstOrNull()
            .orEmpty()

        return when (muscleGroupId) {
            LEGS_GROUP_PREFIX -> MuscleGroup.Legs.find(id)
            CHEST_GROUP_PREFIX -> MuscleGroup.Chest.find(id)
            BACK_GROUP_PREFIX -> MuscleGroup.Back.find(id)
            ARMS_GROUP_PREFIX -> MuscleGroup.Arms.find(id)
            DELTAS_GROUP_PREFIX -> MuscleGroup.Deltas.find(id)
            ABS_PREFIX -> MuscleGroup.Abs
            else -> null
        }
    }

    fun mapMuscleGroupToDb(
        index: Int,
        group: MuscleGroup,
    ): MuscleGroupEntity = MuscleGroupEntity(
        id = index,
        groupId = when (group) {
            MuscleGroup.Abs -> MuscleGroup.ABS_ID
            is MuscleGroup.Arms -> group.id
            is MuscleGroup.Back -> group.id
            is MuscleGroup.Chest -> group.id
            is MuscleGroup.Deltas -> group.id
            is MuscleGroup.Legs -> group.id
        },
    )

    companion object {
        private const val MUSCLE_GROUP_DELIMITER = "_"

        private const val LEGS_GROUP_PREFIX = "legs"
        private const val CHEST_GROUP_PREFIX = "chest"
        private const val BACK_GROUP_PREFIX = "back"
        private const val ARMS_GROUP_PREFIX = "arms"
        private const val DELTAS_GROUP_PREFIX = "deltas"
        private const val ABS_PREFIX = "abs"
    }
}
