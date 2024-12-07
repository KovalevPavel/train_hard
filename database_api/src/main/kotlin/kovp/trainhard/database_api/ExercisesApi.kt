package kovp.trainhard.database_api

import kotlinx.coroutines.flow.Flow
import kovp.trainhard.database_api.models.ExerciseVo

interface ExercisesApi {
    suspend fun addInitExercises(exercises: List<ExerciseVo>)
    suspend fun addNewExercise(exercise: ExerciseVo)
    suspend fun updateExistingExercise(exercise: ExerciseVo): Int
    suspend fun removeExercise(exercise: ExerciseVo)
    fun getExercises(): Flow<List<ExerciseVo>>
    suspend fun getExerciseById(id: String): ExerciseVo?
}
