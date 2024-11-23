package kovp.trainhard.new_trainig_domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.models.ExerciseVo

class GetExerciseByIdInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke(id: String): ExerciseVo? = withContext(Dispatchers.IO) {
        exercisesApi.getExerciseById(id = id)
    }
}
