package me.kovp.trainhard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.kovp.trainhard.database.entities.CompletedSetEntity

@Dao
interface CompletedSetsDao {
    @Query("select * from completedExercises")
    fun getAllSets(): Flow<List<CompletedSetEntity>>

    @Query("select * from completedExercises where date=:date and exerciseId=:exerciseId")
    fun getSetByIdAndExerciseId(date: String, exerciseId: String): List<CompletedSetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompletedExercise(exercise: CompletedSetEntity)

    @Query("select * from completedExercises where exerciseId = :typeId")
    fun getCompletedExercisesByExerciseType(typeId: String): List<CompletedSetEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSet(newSet: CompletedSetEntity)

    @Query("delete from completedExercises where date=:date and exerciseId=:exerciseId")
    fun removeCompletedExercise(date: String, exerciseId: String)
}
