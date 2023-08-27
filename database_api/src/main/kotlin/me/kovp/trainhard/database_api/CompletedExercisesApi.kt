package me.kovp.trainhard.database_api

import me.kovp.trainhard.database_api.models.CompletedExercise
import me.kovp.trainhard.database_api.models.MuscleGroup

interface CompletedExercisesApi {
    suspend fun getCompletedExercisesByMuscleGroup(group: MuscleGroup): List<CompletedExercise>
    suspend fun addNewCompletedExercise(completedExercise: CompletedExercise)
    suspend fun removeCompletedExercise(completedExercise: CompletedExercise)
}
