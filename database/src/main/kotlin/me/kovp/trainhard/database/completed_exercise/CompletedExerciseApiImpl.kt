package me.kovp.trainhard.database.completed_exercise

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.database.MuscleGroupsProvider
import me.kovp.trainhard.database.dao.CompletedExercisesDao
import me.kovp.trainhard.database_api.CompletedExerciseApi
import me.kovp.trainhard.database_api.models.CompletedExercise

internal class CompletedExerciseApiImpl(
    private val completedExercisesDao: CompletedExercisesDao,
    private val completedExerciseMapper: CompletedExerciseMapper,
    private val muscleGroupsProvider: MuscleGroupsProvider,
) : CompletedExerciseApi {
    override suspend fun getAllCompletedExercises(): Flow<List<CompletedExercise>> = completedExercisesDao
        .getAllCompletedExercises()
        .transform { list ->
            val result = list.mapNotNull { entity ->
                completedExerciseMapper.mapToDomain(
                    groups = muscleGroupsProvider(),
                    set = entity,
                )
            }

            emit(result)
        }

    override suspend fun getAllCompletedExercises(date: String): Flow<List<CompletedExercise>> = completedExercisesDao
        .getCompletedExercisesByDate(date = date)
        .transform { list ->
            val result = list.mapNotNull { entity ->
                completedExerciseMapper.mapToDomain(
                    groups = muscleGroupsProvider(),
                    set = entity,
                )
            }

            emit(result)
        }

    override suspend fun getCompletedExercisesByDateAndExercise(
        date: String,
        exerciseId: String,
    ): List<CompletedExercise> = completedExercisesDao.getCompletedExercisesByIdAndExerciseId(date, exerciseId)
        .mapNotNull { entity ->
            completedExerciseMapper.mapToDomain(groups = muscleGroupsProvider(), set = entity)
        }

    override suspend fun getCompletedExercisesByMuscleGroup(
        group: MuscleGroup,
    ): List<CompletedExercise> = completedExercisesDao.getCompletedExercisesByExerciseType(
        typeId = MuscleGroup.getGroupId(group),
    )
        .mapNotNull { entity ->
            completedExerciseMapper.mapToDomain(groups = muscleGroupsProvider(), set = entity)
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
