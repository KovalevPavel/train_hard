package me.kovp.trainhard.database.completed_exercise

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import me.kovp.trainhard.database.dao.CompletedExercisesDao
import me.kovp.trainhard.database_api.CompletedExerciseApi
import me.kovp.trainhard.database_api.models.CompletedExercise

internal class CompletedExerciseApiImpl(
    private val completedExercisesDao: CompletedExercisesDao,
    private val completedExerciseMapper: CompletedExerciseMapper,
) : CompletedExerciseApi {
    override suspend fun getAllCompletedExercises(date: String): Flow<List<CompletedExercise>> =
        completedExercisesDao
            .getCompletedExercisesByDate(date = date)
            .transform { list ->
                val result = list.mapNotNull { entity ->
                    completedExerciseMapper.mapToDomain(completedExercise = entity)
                }

                emit(result)
            }

    override suspend fun getCompletedExercisesByDateAndExercise(
        date: String,
        exerciseId: String,
    ): List<CompletedExercise> =
        completedExercisesDao.getCompletedExercisesByIdAndExerciseId(date, exerciseId)
            .mapNotNull { entity ->
                completedExerciseMapper.mapToDomain(completedExercise = entity)
            }

    override suspend fun addNewCompletedExercise(newCompletedExercise: CompletedExercise) {
        completedExerciseMapper.mapToDb(completedExercise = newCompletedExercise)
            .let { completedExercisesDao.insertCompletedExercise(it) }
    }

    override suspend fun editCompletedExercise(completedExercise: CompletedExercise): Int {
        return completedExerciseMapper.mapToDb(completedExercise = completedExercise)
            .let { completedExercisesDao.updateCompletedExercise(it) }
    }

    override suspend fun removeCompletedExercise(completedExercise: CompletedExercise): Int {
        return completedExercisesDao.removeCompletedExercise(
            id = completedExercise.id,
            date = completedExercise.date,
            exerciseId = completedExercise.exercise.title,
        )
    }
}
