package me.kovp.trainhard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.kovp.trainhard.database.entities.CompletedExerciseEntity

@Dao
interface CompletedExercisesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCompletedExercise(exercise: CompletedExerciseEntity)

    @Query("select * from completedExercises where exerciseId = :typeId")
    fun getCompletedExercisesByExerciseType(typeId: String): List<CompletedExerciseEntity>

    @Query("delete from completedExercises where id=:id")
    fun removeCompletedExercise(id: Long)
}
