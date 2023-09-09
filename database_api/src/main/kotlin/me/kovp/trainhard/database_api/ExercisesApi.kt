package me.kovp.trainhard.database_api

import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.database_api.models.Exercise

interface ExercisesApi {
    suspend fun addInitExercises(exercises: List<Exercise>)
    suspend fun addNewExercise(exercise: Exercise)
    suspend fun getExercises(): List<Exercise>
    suspend fun getExerciseById(id: String): Exercise?
    suspend fun getExercisesByMuscleGroup(muscleGroup: MuscleGroup): List<Exercise>
}
