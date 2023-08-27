package me.kovp.trainhard.database.completed_set

import me.kovp.trainhard.database.dao.ExerciseDao
import me.kovp.trainhard.database.entities.CompletedSetEntity
import me.kovp.trainhard.database.entities.MuscleGroupEntity
import me.kovp.trainhard.database.exercises.ExerciseMapper
import me.kovp.trainhard.database_api.models.CompletedSet

internal class CompletedSetMapper(
    private val exerciseDao: ExerciseDao,
    private val exerciseMapper: ExerciseMapper,
) {
    fun mapToDb(set: CompletedSet): CompletedSetEntity = CompletedSetEntity(
        date = set.date,
        exerciseId = set.exercise.title,
        reps = set.reps,
    )

    suspend fun mapToDomain(
        groups: List<MuscleGroupEntity>,
        set: CompletedSetEntity
    ): CompletedSet? {
        val exercise = exerciseDao.getExerciseByTitle(set.exerciseId)
            .firstOrNull()
            ?.let {
                exerciseMapper.mapToDomain(
                    groups = groups,
                    data = it,
                )
            }
            ?: return null

        return CompletedSet(
            date = set.date,
            exercise = exercise,
            reps = set.reps
        )
    }
}
