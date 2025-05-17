package kovp.trainhard.database.completed_exercise

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kovp.trainhard.database.dao.CompletedExercisesDao
import kovp.trainhard.database_api.CompletedExerciseApi
import kovp.trainhard.database_api.models.CompletedExercise

internal class CompletedExerciseApiImpl(
    private val completedExercisesDao: CompletedExercisesDao,
    private val completedExerciseMapper: CompletedExerciseMapper,
) : CompletedExerciseApi {
    override fun getAllCompletedExercises(timestamp: Long): Flow<List<CompletedExercise>> =
        completedExercisesDao
            .getCompletedExercisesByDate(timestamp = timestamp)
            .transform { list ->
                val result = list.mapNotNull { entity ->
                    completedExerciseMapper.mapToDomain(completedExercise = entity)
                }

                emit(result)
            }

    override suspend fun getCompletedExercisesByDateAndExercise(
        timestamp: Long,
        exerciseId: String,
    ): List<CompletedExercise> =
        completedExercisesDao.getCompletedExercisesByIdAndExerciseId(timestamp, exerciseId)
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
            timestamp = completedExercise.dayTimestamp,
            exerciseId = completedExercise.exercise.title,
        )
    }
}
