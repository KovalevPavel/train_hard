package me.kovp.trainhard.parameters_domain

import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise

class RemoveExerciseInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(exercise: Exercise) {
        exercisesApi.removeExercise(exercise)
    }
}
