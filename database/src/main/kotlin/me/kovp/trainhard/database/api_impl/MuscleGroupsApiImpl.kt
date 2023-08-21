package me.kovp.trainhard.database.api_impl

import me.kovp.trainhard.database.dao.MuscleGroupDao
import me.kovp.trainhard.database_api.MuscleGroupsApi
import me.kovp.trainhard.database_api.models.MuscleGroup
import me.kovp.trainhard.database.entities.MuscleGroupEntity

class MuscleGroupsApiImpl(
    private val muscleGroupDao: MuscleGroupDao,
) : MuscleGroupsApi {
    override fun getMuscleGroups(): List<MuscleGroup> {
        return muscleGroupDao
            .getMuscleGroups()
            .mapNotNull(::mapMuscleGroupToDomain)
    }

    override fun getMuscleGroupById(id: String): MuscleGroup? = mapMuscleGroupToDomainById(id)

    private fun mapMuscleGroupToDomain(
        entity: MuscleGroupEntity,
    ): MuscleGroup? = mapMuscleGroupToDomainById(id = entity.id)

    private fun mapMuscleGroupToDomainById(
        id: String,
    ): MuscleGroup? {
        val (muscleGroupId, muscleId) = id
            .split(MUSCLE_GROUP_DELIMITER)
            .let { list ->
                list.firstOrNull().orEmpty() to list.getOrNull(1).orEmpty()
            }

        return when (muscleGroupId) {
            LEGS_GROUP_PREFIX -> MuscleGroup.Legs.find(muscleId)
            CHEST_GROUP_PREFIX -> MuscleGroup.Chest.find(muscleId)
            BACK_GROUP_PREFIX -> MuscleGroup.Back.find(muscleId)
            ARMS_GROUP_PREFIX -> MuscleGroup.Arms.find(muscleId)
            DELTAS_GROUP_PREFIX -> MuscleGroup.Arms.find(muscleId)
            ABS_PREFIX -> MuscleGroup.Abs
            else -> null
        }
    }

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
