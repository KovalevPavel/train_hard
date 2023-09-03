package me.kovp.trainhard

import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.database_api.MuscleGroupsApi

class InitMuscleGroupsInteractor(
    private val muscleGroupsApi: MuscleGroupsApi,
) {
    suspend operator fun invoke() {
        MuscleGroup.getAllGroups().let {
            muscleGroupsApi.insertMuscleGroups(it)
        }
    }
}
