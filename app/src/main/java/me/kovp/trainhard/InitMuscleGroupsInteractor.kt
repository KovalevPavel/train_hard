package me.kovp.trainhard

import me.kovp.trainhard.database_api.MuscleGroupsApi
import me.kovp.trainhard.database_api.models.MuscleGroup

class InitMuscleGroupsInteractor(
    private val muscleGroupsApi: MuscleGroupsApi,
) {
    suspend operator fun invoke() {
        MuscleGroup.getAllGroups().let {
            muscleGroupsApi.insertMuscleGroups(it)
        }
    }
}
