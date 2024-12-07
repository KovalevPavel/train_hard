package kovp.trainhard.parameters_domain

import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.models.ExerciseVo

class InsertNewExerciseInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(exercise: ExerciseVo) {
        exercisesApi.addNewExercise(exercise)
    }
}
