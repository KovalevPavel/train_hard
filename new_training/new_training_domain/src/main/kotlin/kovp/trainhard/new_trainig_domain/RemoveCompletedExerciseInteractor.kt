package kovp.trainhard.new_trainig_domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kovp.trainhard.database_api.CompletedExerciseApi
import kovp.trainhard.database_api.models.CompletedExercise

class RemoveCompletedExerciseInteractor(
    private val completedExerciseApi: CompletedExerciseApi,
) {
    suspend operator fun invoke(
        editedExercise: CompletedExercise,
    ): Int = withContext(Dispatchers.IO) {
        completedExerciseApi.removeCompletedExercise(completedExercise = editedExercise)
    }
}
