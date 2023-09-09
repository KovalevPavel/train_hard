package me.kovp.trainhard.database_api

import kotlinx.coroutines.flow.Flow
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.database_api.models.CompletedExercise

interface CompletedExerciseApi {
    suspend fun getAllCompletedExercises(): Flow<List<CompletedExercise>>
    suspend fun getAllCompletedExercises(date: String): Flow<List<CompletedExercise>>
    suspend fun getCompletedExercisesByDateAndExercise(
        date: String,
        exerciseId: String,
    ): List<CompletedExercise>

    suspend fun getCompletedExercisesByMuscleGroup(group: MuscleGroup): List<CompletedExercise>
    suspend fun addNewCompletedExercise(newCompletedExercise: CompletedExercise)
    suspend fun editCompletedExercise(completedExercise: CompletedExercise): Int
    suspend fun removeCompletedExercise(completedExercise: CompletedExercise): Int
}
