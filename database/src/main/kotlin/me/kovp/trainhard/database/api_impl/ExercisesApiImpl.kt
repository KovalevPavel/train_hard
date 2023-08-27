package me.kovp.trainhard.database.api_impl

import me.kovp.trainhard.database.dao.ExerciseDao
import me.kovp.trainhard.database.dao.MuscleGroupDao
import me.kovp.trainhard.database.entities.MuscleGroupEntity
import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.database_api.models.MuscleGroup

internal class ExercisesApiImpl(
    private val exerciseDao: ExerciseDao,
    private val muscleGroupDao: MuscleGroupDao,
    private val exerciseMapper: ExerciseMapper,
) : ExercisesApi {

    private var muscleGroups: List<MuscleGroupEntity>? = null

    override suspend fun addInitExercises(exercises: List<Exercise>) {
        exercises.map(exerciseMapper::mapToDb)
            .let { exerciseDao.initBaseExercises(it) }
    }

    override suspend fun addNewExercise(exercise: Exercise) {
        exercise.let(exerciseMapper::mapToDb)
            .let { exerciseDao.insertExercise(it) }
    }

    override suspend fun getExercises(): List<Exercise> {
        val groups = getMuscleGroupsAndReturn()

        return exerciseDao.getExercises()
            .map { exerciseMapper.mapToDomain(groups, it) }
    }

    override suspend fun getExercisesByMuscleGroup(muscleGroup: MuscleGroup): List<Exercise> {
        val groups = getMuscleGroupsAndReturn()

        val groupId = groups.firstOrNull { e ->
            e.groupId.equals(MuscleGroup.getGroupId(muscleGroup), ignoreCase = true)
        }
            ?.id
            ?: return emptyList()

        return exerciseDao.getExercisesByMuscleGroup(groupId).map {
            exerciseMapper.mapToDomain(groups, it)
        }
    }

    private suspend fun getMuscleGroupsAndReturn(): List<MuscleGroupEntity> {
        return muscleGroups ?: kotlin.run {
            muscleGroupDao.getMuscleGroups().also { muscleGroups = it }
        }
    }
}
