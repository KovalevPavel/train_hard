package me.kovp.trainhard.new_trainig_domain

import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.database_api.MuscleGroupsApi

class GetMuscleGroupsInteractor(
    private val muscleGroupsApi: MuscleGroupsApi,
) {
    suspend operator fun invoke(): List<MuscleGroup> {
        return muscleGroupsApi.getMuscleGroups()
    }
}
