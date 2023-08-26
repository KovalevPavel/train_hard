package me.kovp.trainhard.new_trainig_domain

import me.kovp.trainhard.database_api.MuscleGroupsApi
import me.kovp.trainhard.database_api.models.MuscleGroup

class GetMuscleGroupsInteractor(
    private val muscleGroupsApi: MuscleGroupsApi,
) {
    suspend operator fun invoke(): List<MuscleGroup> {
        return muscleGroupsApi.getMuscleGroups()
    }
}
