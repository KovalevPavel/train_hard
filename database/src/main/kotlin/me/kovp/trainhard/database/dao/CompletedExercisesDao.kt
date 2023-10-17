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

    @Query("select * from completedExercises where dayTimestamp=:timestamp")
    fun getCompletedExercisesByDate(timestamp: Long): Flow<List<CompletedExerciseEntity>>

    @Query("select * from completedExercises where dayTimestamp=:timestamp and exerciseId=:exerciseId")
    suspend fun getCompletedExercisesByIdAndExerciseId(
        timestamp: Long,
        exerciseId: String,
    ): List<CompletedExerciseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletedExercise(exercise: CompletedExerciseEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCompletedExercise(newExercise: CompletedExerciseEntity): Int

    @Query(
        "delete from completedExercises where id=:id and dayTimestamp=:timestamp and exerciseId=:exerciseId"
    )
    suspend fun removeCompletedExercise(id: Long, timestamp: Long, exerciseId: String): Int
}
