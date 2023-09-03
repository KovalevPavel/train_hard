package me.kovp.trainhard.database.completed_set

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.database.MuscleGroupsProvider
import me.kovp.trainhard.database.dao.CompletedSetsDao
import me.kovp.trainhard.database_api.CompletedSetsApi
import me.kovp.trainhard.database_api.models.CompletedSet

internal class CompletedSetsApiImpl(
    private val completedSetsDao: CompletedSetsDao,
    private val completedSetMapper: CompletedSetMapper,
    private val muscleGroupsProvider: MuscleGroupsProvider,
) : CompletedSetsApi {
    override suspend fun getAllSets(): Flow<List<CompletedSet>> = completedSetsDao
        .getAllSets()
        .transform { list ->
            val result = list.mapNotNull { entity ->
                completedSetMapper.mapToDomain(
                    groups = muscleGroupsProvider(),
                    set = entity,
                )
            }

            emit(result)
        }

    override suspend fun getSetByDateAndExercise(
        date: String,
        exerciseId: String,
    ): CompletedSet? = completedSetsDao.getSetByIdAndExerciseId(date, exerciseId)
        .firstOrNull()
        ?.let { entity ->
            completedSetMapper.mapToDomain(groups = muscleGroupsProvider(), set = entity)
        }

    override suspend fun getCompletedSetsByMuscleGroup(
        group: MuscleGroup,
    ): List<CompletedSet> = completedSetsDao.getCompletedExercisesByExerciseType(
        typeId = MuscleGroup.getGroupId(group),
    )
        .mapNotNull { entity ->
            completedSetMapper.mapToDomain(groups = muscleGroupsProvider(), set = entity)
        }

    override suspend fun addNewCompletedSet(newSet: CompletedSet) {
        completedSetMapper.mapToDb(set = newSet)
            .let { completedSetsDao.insertCompletedExercise(it) }
    }

    override suspend fun editSet(newSet: CompletedSet) {
        completedSetMapper.mapToDb(set = newSet)
            .let { completedSetsDao.updateSet(it) }
    }

    override suspend fun removeCompletedSet(completedSet: CompletedSet) {
        completedSetsDao.removeCompletedExercise(
            date = completedSet.date,
            exerciseId = completedSet.exercise.title,
        )
    }
}
