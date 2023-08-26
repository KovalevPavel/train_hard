package me.kovp.trainhard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.kovp.trainhard.database.entities.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun initBaseExercises(exercises: List<ExerciseEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Query("select * from exercises")
    suspend fun getExercises(): List<ExerciseEntity>

    @Query("select * from exercises where muscleGroups like :group")
    suspend fun getExercisesByMuscleGroup(group: Int): List<ExerciseEntity>
}