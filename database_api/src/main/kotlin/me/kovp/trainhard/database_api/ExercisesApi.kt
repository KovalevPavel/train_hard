package me.kovp.trainhard.database_api

import kotlinx.coroutines.flow.Flow
import me.kovp.trainhard.database_api.models.Exercise

interface ExercisesApi {
    suspend fun addInitExercises(exercises: List<Exercise>)
    suspend fun addNewExercise(exercise: Exercise)
    suspend fun updateExistingExercise(exercise: Exercise): Int
    suspend fun removeExercise(exercise: Exercise)
    suspend fun getExercises(): Flow<List<Exercise>>
    suspend fun getExerciseById(id: String): Exercise?
}
