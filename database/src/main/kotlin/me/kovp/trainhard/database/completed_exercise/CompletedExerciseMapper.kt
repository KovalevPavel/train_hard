package me.kovp.trainhard.database.completed_exercise

import me.kovp.trainhard.database.dao.ExerciseDao
import me.kovp.trainhard.database.entities.CompletedExerciseEntity
import me.kovp.trainhard.database.exercises.ExerciseMapper
import me.kovp.trainhard.database_api.models.CompletedExercise

internal class CompletedExerciseMapper(
    private val exerciseDao: ExerciseDao,
    private val exerciseMapper: ExerciseMapper,
) {
    fun mapToDb(
        completedExercise: CompletedExercise,
    ): CompletedExerciseEntity = CompletedExerciseEntity(
        id = completedExercise.id,
        dayTimestamp = completedExercise.dayTimestamp,
        exerciseId = completedExercise.exercise.title,
        sets = completedExercise.sets,
    )

    suspend fun mapToDomain(
        completedExercise: CompletedExerciseEntity,
    ): CompletedExercise? {
        val exercise = exerciseDao.getExerciseByTitle(completedExercise.exerciseId)
            .firstOrNull()
            ?.let(exerciseMapper::mapToDomain)
            ?: return null

        return CompletedExercise(
            id = completedExercise.id,
            dayTimestamp = completedExercise.dayTimestamp,
            exercise = exercise,
            sets = completedExercise.sets,
        )
    }
}
