package me.kovp.trainhard.database_api

import me.kovp.trainhard.core_domain.MuscleGroup

interface MuscleGroupsApi {
    suspend fun getMuscleGroups(): List<MuscleGroup>
    suspend fun getMuscleGroupById(id: String): MuscleGroup?
    suspend fun insertMuscleGroups(groups: List<MuscleGroup>)
}
