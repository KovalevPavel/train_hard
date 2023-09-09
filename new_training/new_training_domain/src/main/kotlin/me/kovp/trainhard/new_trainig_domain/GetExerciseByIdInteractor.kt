package me.kovp.trainhard.new_trainig_domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise

class GetExerciseByIdInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(id: String): Exercise? = withContext(Dispatchers.IO) {
        exercisesApi.getExerciseById(id = id)
    }
}