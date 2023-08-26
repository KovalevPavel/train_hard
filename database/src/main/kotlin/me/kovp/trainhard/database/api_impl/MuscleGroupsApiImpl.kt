package me.kovp.trainhard.database.api_impl

import me.kovp.trainhard.database.dao.MuscleGroupDao
import me.kovp.trainhard.database_api.MuscleGroupsApi
import me.kovp.trainhard.database_api.models.MuscleGroup
import me.kovp.trainhard.database.entities.MuscleGroupEntity

class MuscleGroupsApiImpl internal constructor(
    private val muscleGroupDao: MuscleGroupDao,
    private val muscleGroupMapper: MuscleGroupsMapper,
) : MuscleGroupsApi {
    override suspend fun getMuscleGroups(): List<MuscleGroup> {
        return muscleGroupDao
            .getMuscleGroups()
            .mapNotNull(::mapMuscleGroupToDomain)
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
