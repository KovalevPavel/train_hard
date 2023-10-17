package me.kovp.trainhard.new_trainig_domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.kovp.trainhard.core_domain.DATE_FORMAT_dd_MM_yyyy
import me.kovp.trainhard.database_api.CompletedExerciseApi
import me.kovp.trainhard.database_api.models.CompletedExercise
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.database_api.models.Sets
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
            val dateTimestamp = LocalDate.parse(
                dateString,
                DateTimeFormatter.ofPattern(DATE_FORMAT_dd_MM_yyyy),
            )
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

            addNewRecord(
                dateString = dateString,
                completedExerciseId = nextExerciseId,
                exercise = exercise,
                sets = sets,
                timestamp = dateTimestamp,
            )
        }
    }

    private suspend fun addNewRecord(
        dateString: String,
        completedExerciseId: Long,
        exercise: Exercise,
        sets: Sets,
        timestamp: Long,
    ) {
        CompletedExercise(
            id = completedExerciseId,
            date = dateString,
            exercise = exercise,
            sets = sets,
            dayTimestamp = timestamp,
        )
            .let {
                completedExerciseApi.addNewCompletedExercise(it)
            }
    }
}
