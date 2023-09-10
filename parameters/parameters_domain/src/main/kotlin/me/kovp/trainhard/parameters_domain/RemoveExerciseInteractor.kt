package me.kovp.trainhard.parameters_domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise

class RemoveExerciseInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exercisesApi.removeExercise(exercise)
        }
    }
}
