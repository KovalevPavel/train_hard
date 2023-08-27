package me.kovp.trainhard.database_api

import kotlinx.coroutines.flow.Flow
import me.kovp.trainhard.database_api.models.CompletedSet
import me.kovp.trainhard.database_api.models.MuscleGroup

interface CompletedSetsApi {
    suspend fun getAllSets(): Flow<List<CompletedSet>>
    suspend fun getSetByDateAndExercise(date: String, exerciseId: String): CompletedSet?
    suspend fun getCompletedSetsByMuscleGroup(group: MuscleGroup): List<CompletedSet>
    suspend fun addNewCompletedSet(newSet: CompletedSet)
    suspend fun editSet(newSet: CompletedSet)
    suspend fun removeCompletedSet(completedSet: CompletedSet)
}
