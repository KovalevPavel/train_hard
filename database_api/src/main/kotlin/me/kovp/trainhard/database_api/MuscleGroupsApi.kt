package me.kovp.trainhard.database_api

import me.kovp.trainhard.database_api.models.MuscleGroup

interface MuscleGroupsApi {
    suspend fun getMuscleGroups(): List<MuscleGroup>
    suspend fun getMuscleGroupById(id: String): MuscleGroup?
    suspend fun insertMuscleGroups(groups: List<MuscleGroup>)
}
