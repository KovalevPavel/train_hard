package me.kovp.trainhard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.kovp.trainhard.database.entities.CompletedExerciseEntity

@Dao
interface CompletedExercisesDao {
    @Query("select * from completedExercises")
    fun getAllCompletedExercises(): Flow<List<CompletedExerciseEntity>>

    @Query("select * from completedExercises where date=:date")
    fun getCompletedExercisesByDate(date: String): Flow<List<CompletedExerciseEntity>>

    @Query("select * from completedExercises where date=:date and exerciseId=:exerciseId")
    fun getCompletedExercisesByIdAndExerciseId(
        date: String,
        exerciseId: String,
    ): List<CompletedExerciseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompletedExercise(exercise: CompletedExerciseEntity)

    @Query("select * from completedExercises where exerciseId = :typeId")
    fun getCompletedExercisesByExerciseType(typeId: String): List<CompletedExerciseEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCompletedExercise(newExercise: CompletedExerciseEntity): Int

    @Query("delete from completedExercises where id=:id and date=:date and exerciseId=:exerciseId")
    fun removeCompletedExercise(id: Long, date: String, exerciseId: String): Int
}
