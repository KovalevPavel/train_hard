package me.kovp.trainhard.database

import me.kovp.trainhard.database.dao.MuscleGroupDao
import me.kovp.trainhard.database.entities.MuscleGroupEntity

internal class MuscleGroupsProvider(
    private val muscleGroupDao: MuscleGroupDao,
) {
    private var muscleGroups: List<MuscleGroupEntity>? = null

    suspend operator fun invoke(): List<MuscleGroupEntity> {
        return muscleGroups ?: muscleGroupDao.getMuscleGroups().also {
            muscleGroups = it
        }
    }
}
