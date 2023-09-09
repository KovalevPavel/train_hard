package me.kovp.trainhard.new_trainig_domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.kovp.trainhard.database_api.CompletedExerciseApi
import me.kovp.trainhard.database_api.models.CompletedExercise
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.database_api.models.Sets

class AddNewCompletedExerciseInteractor(
    private val completedExerciseApi: CompletedExerciseApi,
) {
    suspend operator fun invoke(dateString: String, exercise: Exercise, sets: Sets) {
        withContext(Dispatchers.IO) {
            val existingExercises = completedExerciseApi.getCompletedExercisesByDateAndExercise(
                date = dateString,
                exerciseId = exercise.title,
            )
            val nextExerciseId = existingExercises.size.toLong()
            addNewRecord(
                dateString = dateString,
                completedExerciseId = nextExerciseId,
                exercise = exercise,
                sets = sets,
            )
        }
    }

    private suspend fun addNewRecord(
        dateString: String,
        completedExerciseId: Long,
        exercise: Exercise,
        sets: Sets,
    ) {
        CompletedExercise(
            id = completedExerciseId,
            date = dateString,
            exercise = exercise,
            sets = sets,
        )
            .let {
                completedExerciseApi.addNewCompletedExercise(it)
            }
    }
}
