package me.kovp.trainhard.database_api

import kotlinx.coroutines.flow.Flow
import me.kovp.trainhard.database_api.models.CompletedExercise

interface CompletedExerciseApi {
    fun getAllCompletedExercises(timestamp: Long): Flow<List<CompletedExercise>>
    suspend fun getCompletedExercisesByDateAndExercise(
        timestamp: Long,
        exerciseId: String,
    ): List<CompletedExercise>
    suspend fun addNewCompletedExercise(newCompletedExercise: CompletedExercise)
    suspend fun editCompletedExercise(completedExercise: CompletedExercise): Int
    suspend fun removeCompletedExercise(completedExercise: CompletedExercise): Int
}
