package me.kovp.trainhard.new_trainig_domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.kovp.trainhard.database_api.CompletedExerciseApi
import me.kovp.trainhard.database_api.models.CompletedExercise

class UpdateCompletedExerciseInteractor(
    private val completedExerciseApi: CompletedExerciseApi,
) {
    suspend operator fun invoke(
        editedCompletedExercise: CompletedExercise,
    ): Int = withContext(Dispatchers.IO) {
        completedExerciseApi.editCompletedExercise(completedExercise = editedCompletedExercise)
    }
}
