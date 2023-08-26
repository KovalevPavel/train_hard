package me.kovp.trainhard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.kovp.trainhard.database.entities.MuscleGroupEntity

@Dao
interface MuscleGroupDao {
    @Query("select * from musclegroup")
    suspend fun getMuscleGroups(): List<MuscleGroupEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMuscleGroup(group: MuscleGroupEntity)
}
