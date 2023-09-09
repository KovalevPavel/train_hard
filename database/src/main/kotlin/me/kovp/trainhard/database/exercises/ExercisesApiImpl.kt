package me.kovp.trainhard.database.exercises

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

    override suspend fun getExercises(): List<Exercise> {

        return exerciseDao.getExercises()
            .map(exerciseMapper::mapToDomain)
    }

    override suspend fun getExerciseById(id: String): Exercise? {

        return exerciseDao.getExerciseByTitle(title = id)
            .firstOrNull()
            ?.let(exerciseMapper::mapToDomain)
    }
}
