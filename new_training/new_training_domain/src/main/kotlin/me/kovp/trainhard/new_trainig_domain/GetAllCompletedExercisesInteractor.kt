package me.kovp.trainhard.new_trainig_domain

import me.kovp.trainhard.database_api.CompletedExerciseApi

class GetAllCompletedExercisesInteractor(
    private val exercisesApi: CompletedExerciseApi,
) {
    operator fun invoke(timestamp: Long) = exercisesApi.getAllCompletedExercises(timestamp)
}
