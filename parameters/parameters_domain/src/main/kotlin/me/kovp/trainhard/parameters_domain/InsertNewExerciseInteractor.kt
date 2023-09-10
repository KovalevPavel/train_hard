package me.kovp.trainhard.parameters_domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise

class InsertNewExerciseInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(exercise: Exercise) {
        return withContext(Dispatchers.IO) {
            exercisesApi.addNewExercise(exercise)
        }
    }
}
