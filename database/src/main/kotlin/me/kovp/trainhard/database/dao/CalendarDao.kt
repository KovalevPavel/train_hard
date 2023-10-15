package me.kovp.trainhard.database.dao

import androidx.room.Dao
import androidx.room.MapInfo
import androidx.room.Query

@Dao
interface CalendarDao {
    @MapInfo(keyColumn = "long", valueColumn = "exerciseId")
    @Query(
        "select dayTimestamp as long, group_concat(distinct exerciseId) as exerciseId " +
                "from completedExercises " +
                "where dayTimestamp between :startDate and :endDate"
    )
    suspend fun getMuscleGroupsByDates(
        startDate: Long,
        endDate: Long,
    ): Map<Long, String>
}
