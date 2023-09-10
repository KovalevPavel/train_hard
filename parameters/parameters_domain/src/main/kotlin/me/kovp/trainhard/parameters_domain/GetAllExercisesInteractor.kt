package me.kovp.trainhard.parameters_domain

import kotlinx.coroutines.flow.Flow
import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise

class GetAllExercisesInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(): Flow<List<Exercise>> {
        return exercisesApi.getExercises()
    }
}
