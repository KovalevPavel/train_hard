package me.kovp.trainhard.new_trainig_domain

import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise

class GetAllExercisesInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(): List<Exercise> {
        return exercisesApi.getExercises()
    }
}
