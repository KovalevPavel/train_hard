package me.kovp.trainhard.database.exercises

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import me.kovp.trainhard.database.dao.ExerciseDao
import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise

internal class ExercisesApiImpl(
    private val exerciseDao: ExerciseDao,
    private val exerciseMapper: ExerciseMapper,
) : ExercisesApi {
    override suspend fun addInitExercises(exercises: List<Exercise>) {
        exercises.map(exerciseMapper::mapToDb)
            .let { exerciseDao.initBaseExercises(it) }
    }

    override suspend fun addNewExercise(exercise: Exercise) {
        exercise.let(exerciseMapper::mapToDb)
            .let { exerciseDao.insertExercise(it) }
    }

    override suspend fun getExercises(): Flow<List<Exercise>> {
        return exerciseDao.getExercises()
            .transform {
                val newList = it.map(exerciseMapper::mapToDomain)
                emit(newList)
            }
    }

    override suspend fun getExerciseById(id: String): Exercise? {
        return exerciseDao.getExerciseByTitle(title = id)
            .firstOrNull()
            ?.let(exerciseMapper::mapToDomain)
    }
}
