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
    suspend operator fun invoke(timestamp: Long, exercise: Exercise, sets: Sets) {
        withContext(Dispatchers.IO) {
            val existingExercises = completedExerciseApi.getCompletedExercisesByDateAndExercise(
                timestamp = timestamp,
                exerciseId = exercise.title,
            )
            val nextExerciseId = existingExercises.size.toLong()

            addNewRecord(
                completedExerciseId = nextExerciseId,
                exercise = exercise,
                sets = sets,
                timestamp = timestamp,
            )
        }
    }

    private suspend fun addNewRecord(
        completedExerciseId: Long,
        exercise: Exercise,
        sets: Sets,
        timestamp: Long,
    ) {
        CompletedExercise(
            id = completedExerciseId,
            exercise = exercise,
            sets = sets,
            dayTimestamp = timestamp,
        )
            .let {
                completedExerciseApi.addNewCompletedExercise(it)
            }
    }
}
