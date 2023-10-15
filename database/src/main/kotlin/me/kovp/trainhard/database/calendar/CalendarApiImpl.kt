package me.kovp.trainhard.database.calendar

import androidx.room.withTransaction
import me.kovp.trainhard.core_domain.Muscle
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.database.AppDatabase
import me.kovp.trainhard.database.exercises.ExerciseMapper
import me.kovp.trainhard.database_api.CalendarApi
import me.kovp.trainhard.database_api.models.Exercise

internal class CalendarApiImpl(
    private val database: AppDatabase,
    private val exerciseMapper: ExerciseMapper,
) : CalendarApi {
    override suspend fun getMuscleGroupsByDates(
        startDate: Long,
        endDate: Long
    ): Map<Long, List<MuscleGroup>> = database.withTransaction {
        val exercisesIdsInDates = database.calendarDao().getMuscleGroupsByDates(startDate, endDate)

        val uniqueExercises = exercisesIdsInDates.values
            .map { it.split(EXERCISES_IDS_DELIMITER) }
            .flatten()
            .mapNotNull { exerciseId ->
                database.exercisesDao().getExerciseByTitle(exerciseId).firstOrNull()
                    ?: return@mapNotNull null
            }

        val uniqueMuscleGroups = uniqueExercises.asSequence()
            .map(exerciseMapper::mapToDomain)
            .map(Exercise::muscles)
            .flatten()
            .map(Muscle::muscleGroup)
            .distinct()
            .toList()

        exercisesIdsInDates.mapValues { uniqueMuscleGroups }
    }

    companion object {
        private const val EXERCISES_IDS_DELIMITER = ","
    }
}
