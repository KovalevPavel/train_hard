package kovp.trainhard.new_trainig_domain

import kovp.trainhard.database_api.CompletedExerciseApi

class GetAllCompletedExercisesInteractor(
    private val exercisesApi: CompletedExerciseApi,
) {
    operator fun invoke(timestamp: Long) = exercisesApi.getAllCompletedExercises(timestamp)
}
