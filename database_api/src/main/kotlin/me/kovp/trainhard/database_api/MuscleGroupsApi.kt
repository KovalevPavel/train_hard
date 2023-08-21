package me.kovp.trainhard.database_api

import me.kovp.trainhard.database_api.models.MuscleGroup

interface MuscleGroupsApi {
    fun getMuscleGroups(): List<MuscleGroup>
    fun getMuscleGroupById(id: String): MuscleGroup?
}
