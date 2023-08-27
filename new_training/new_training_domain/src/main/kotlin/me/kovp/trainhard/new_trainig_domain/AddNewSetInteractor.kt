package me.kovp.trainhard.new_trainig_domain

import me.kovp.trainhard.core.DATE_FORMAT_dd_MM_yyyy
import me.kovp.trainhard.core.formatToDateString
import me.kovp.trainhard.database_api.CompletedSetsApi
import me.kovp.trainhard.database_api.models.CompletedSet
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.database_api.models.Reps
import me.kovp.trainhard.database_api.models.plus

class AddNewSetInteractor(
    private val completedSetsApi: CompletedSetsApi,
) {
    suspend operator fun invoke(exercise: Exercise, reps: Reps) {
        val existingExercise = completedSetsApi.getSetByDateAndExercise(
            date = System.currentTimeMillis().formatToDateString(DATE_FORMAT_dd_MM_yyyy),
            exerciseId = exercise.title,
        )

        when (existingExercise) {
            null -> addNewRecord(exercise = exercise, reps = reps)
            else -> updateExistingRecord(existingSet = existingExercise, repsToAdd = reps)
        }
    }

    private suspend fun addNewRecord(exercise: Exercise, reps: Reps) {
        CompletedSet(
            date = System.currentTimeMillis().formatToDateString(DATE_FORMAT_dd_MM_yyyy),
            exercise = exercise,
            reps = reps,
        )
            .let {
                completedSetsApi.addNewCompletedSet(it)
            }
    }

    private suspend fun updateExistingRecord(
        existingSet: CompletedSet,
        repsToAdd: Reps,
    ) {
        completedSetsApi.editSet(newSet = existingSet + repsToAdd)
    }
}
