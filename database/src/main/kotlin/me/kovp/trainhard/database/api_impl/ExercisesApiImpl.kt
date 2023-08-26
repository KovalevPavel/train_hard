package me.kovp.trainhard.database.api_impl

import me.kovp.trainhard.database.dao.ExerciseDao
import me.kovp.trainhard.database.dao.MuscleGroupDao
import me.kovp.trainhard.database.entities.ExerciseEntity
import me.kovp.trainhard.database.entities.MuscleGroupEntity
import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.database_api.models.MuscleGroup

internal class ExercisesApiImpl(
    private val exerciseDao: ExerciseDao,
    private val muscleGroupDao: MuscleGroupDao,
    private val muscleGroupMapper: MuscleGroupsMapper,
) : ExercisesApi {

    private var muscleGroups: List<MuscleGroupEntity>? = null

    override suspend fun addInitExercises(exercises: List<Exercise>) {
        val groups = getMuscleGroupsAndReturn()

        exercises.map {
            ExerciseEntity(
                title = it.title,
                muscleGroups = it.muscleGroups.mapNotNull {
                    groups.firstOrNull { e ->
                        e.groupId.equals(MuscleGroup.getGroupId(it), ignoreCase = true)
                    }
                        ?.id
                        ?: return@mapNotNull null
                }
            )
        }
            .let {
                exerciseDao.initBaseExercises(it)
            }
    }

    override suspend fun addNewExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise.toDb())
    }

    override suspend fun getExercises(): List<Exercise> {
        val groups = getMuscleGroupsAndReturn()
        val groupsIds = groups.map(MuscleGroupEntity::id)

        return exerciseDao.getExercises().map {
            val exerciseGroups = mutableListOf<String>()

            it.muscleGroups.forEach { id ->
                if (id in groupsIds) {
                    groups.firstOrNull { g -> g.id == id }?.groupId?.let(exerciseGroups::add)
                }
            }

            Exercise(
                title = it.title,
                muscleGroups = exerciseGroups.mapNotNull(muscleGroupMapper::mapMuscleGroupToDomainById)
            )
        }
    }

    override suspend fun getExercisesByMuscleGroup(muscleGroup: MuscleGroup): List<Exercise> {
        val groups = getMuscleGroupsAndReturn()

        val groupId = groups.firstOrNull { e ->
            e.groupId.equals(MuscleGroup.getGroupId(muscleGroup), ignoreCase = true)
        }
            ?.id
            ?: return emptyList()

        return exerciseDao.getExercisesByMuscleGroup(groupId).map {

            Exercise(
                title = it.title,
                muscleGroups = groups.map(MuscleGroupEntity::groupId)
                    .mapNotNull(muscleGroupMapper::mapMuscleGroupToDomainById),
            )
        }
    }

    private suspend fun getMuscleGroupsAndReturn(): List<MuscleGroupEntity> {
        return muscleGroups ?: kotlin.run {
            muscleGroupDao.getMuscleGroups().also { muscleGroups = it }
        }
    }

    private fun Exercise.toDb() = ExerciseEntity(
        title = title,
        muscleGroups = muscleGroups
            .mapIndexed(muscleGroupMapper::mapMuscleGroupToDb)
            .map(MuscleGroupEntity::id),
    )
}
