package kovp.trainhard.parameters_domain

import kotlinx.coroutines.flow.Flow
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.models.Exercise

class GetAllExercisesInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(): Flow<List<Exercise>> = exercisesApi.getExercises()
}
