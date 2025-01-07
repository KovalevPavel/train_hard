package kovp.trainhard.parameters_core

import kotlinx.coroutines.flow.Flow
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.models.ExerciseVo

class GetAllExercisesInteractor(
    private val exercisesApi: ExercisesApi,
) {
    operator fun invoke(): Flow<List<ExerciseVo>> = exercisesApi.getExercises()
}
