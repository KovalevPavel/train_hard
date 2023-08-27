package me.kovp.trainhard.database.api_impl

import me.kovp.trainhard.database.dao.CompletedExercisesDao
import me.kovp.trainhard.database.dao.ExerciseDao
import me.kovp.trainhard.database.dao.MuscleGroupDao
import me.kovp.trainhard.database.entities.CompletedExerciseEntity
import me.kovp.trainhard.database_api.CompletedExercisesApi
import me.kovp.trainhard.database_api.models.CompletedExercise
import me.kovp.trainhard.database_api.models.MuscleGroup

internal class CompletedExercisesApiImpl(
    private val completedExercisesDao: CompletedExercisesDao,
    private val exerciseDao: ExerciseDao,
    private val exerciseMapper: ExerciseMapper,
    private val muscleGroupDao: MuscleGroupDao,
) : CompletedExercisesApi {

    override suspend fun getCompletedExercisesByMuscleGroup(group: MuscleGroup): List<CompletedExercise> {
        val groups = muscleGroupDao.getMuscleGroups()

        return completedExercisesDao.getCompletedExercisesByExerciseType(
            typeId = MuscleGroup.getGroupId(group)
        )
            .mapNotNull { entity ->
                val exercise = exerciseDao.getExerciseByTitle(entity.exerciseId)
                    .firstOrNull()
                    ?.let {
                        exerciseMapper.mapToDomain(
                            groups = groups,
                            data = it,
                        )
                    }
                    ?: return@mapNotNull null

                CompletedExercise(
                    id = entity.id,
                    exercise = exercise,
                    reps = entity.reps
                )
            }
    }

    override suspend fun addNewCompletedExercise(completedExercise: CompletedExercise) {
        CompletedExerciseEntity(
            id = 0,
            exerciseId = completedExercise.exercise.title,
            reps = completedExercise.reps,
        )
            .let { completedExercisesDao.insertCompletedExercise(it) }
    }

    override suspend fun removeCompletedExercise(completedExercise: CompletedExercise) {
        completedExercisesDao.removeCompletedExercise(id = completedExercise.id)
    }
}
