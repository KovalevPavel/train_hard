package kovp.trainhard.database.exercises

import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kovp.trainhard.database.dao.ExerciseDao
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.errors.EntityExistsException
import kovp.trainhard.database_api.models.ExerciseVo

internal class ExercisesApiImpl(
    private val exerciseDao: ExerciseDao,
    private val exerciseMapper: ExerciseMapper,
) : ExercisesApi {
    override suspend fun addInitExercises(exercises: List<ExerciseVo>) {
        exercises.map(exerciseMapper::mapToDb)
            .let { exerciseDao.initBaseExercises(it) }
    }

    override suspend fun addNewExercise(exercise: ExerciseVo) {
        exercise.let(exerciseMapper::mapToDb)
            .let {
                kotlin.runCatching {
                    exerciseDao.insertExercise(it)
                }
                    .onFailure {
                        throw when (it) {
                            is SQLiteConstraintException -> EntityExistsException(title = exercise.title)
                            else -> it
                        }
                    }
            }
    }

    override suspend fun updateExistingExercise(exercise: ExerciseVo): Int {
        return exerciseMapper.mapToDb(data = exercise)
            .let { exerciseDao.updateExercise(it) }
    }

    override suspend fun removeExercise(exercise: ExerciseVo) {
        exerciseDao.removeExercise(exerciseTitle = exercise.title)
    }

    override suspend fun getExercises(): Flow<List<ExerciseVo>> {
        return exerciseDao.getExercises()
            .transform {
                val newList = it.map(exerciseMapper::mapToDomain)
                emit(newList)
            }
    }

    override suspend fun getExerciseById(id: String): ExerciseVo? {
        return exerciseDao.getExerciseByTitle(title = id)
            .firstOrNull()
            ?.let(exerciseMapper::mapToDomain)
    }
}
