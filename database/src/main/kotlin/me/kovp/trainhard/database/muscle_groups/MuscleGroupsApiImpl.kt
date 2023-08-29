package me.kovp.trainhard.database.muscle_groups

import me.kovp.trainhard.database.MuscleGroupsProvider
import me.kovp.trainhard.database.dao.MuscleGroupDao
import me.kovp.trainhard.database.entities.MuscleGroupEntity
import me.kovp.trainhard.database_api.MuscleGroupsApi
import me.kovp.trainhard.database_api.models.MuscleGroup

class MuscleGroupsApiImpl internal constructor(
    private val muscleGroupDao: MuscleGroupDao,
    private val muscleGroupMapper: MuscleGroupsMapper,
    private val muscleGroupsProvider: MuscleGroupsProvider,
) : MuscleGroupsApi {
    override suspend fun getMuscleGroups(): List<MuscleGroup> {
        return muscleGroupsProvider().mapNotNull(::mapMuscleGroupToDomain)
    }

    override suspend fun getMuscleGroupById(id: String): MuscleGroup? = muscleGroupMapper
        .mapMuscleGroupToDomainById(id)

    override suspend fun insertMuscleGroups(groups: List<MuscleGroup>) {
        groups.mapIndexed(muscleGroupMapper::mapMuscleGroupToDb)
            .forEach { e -> muscleGroupDao.insertMuscleGroup(e) }
    }

    private fun mapMuscleGroupToDomain(
        entity: MuscleGroupEntity,
    ): MuscleGroup? = entity.groupId.let(muscleGroupMapper::mapMuscleGroupToDomainById)
}
