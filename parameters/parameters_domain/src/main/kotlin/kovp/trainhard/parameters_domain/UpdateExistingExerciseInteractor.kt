package kovp.trainhard.parameters_domain

import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.models.ExerciseVo

class UpdateExistingExerciseInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(exercise: ExerciseVo): Int =
        exercisesApi.updateExistingExercise(exercise)
}
