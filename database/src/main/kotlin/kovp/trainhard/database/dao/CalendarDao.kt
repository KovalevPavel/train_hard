package kovp.trainhard.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kovp.trainhard.database.entities.MusclesInDayTuple

@Dao
interface CalendarDao {
    @Query(
        "select completedExercises.dayTimestamp as timestamp, exercises.muscles as muscles " +
                "from completedExercises, exercises " +
                "where (dayTimestamp between :startDate and :endDate) and " +
                "(completedExercises.exerciseId = exercises.title)"
    )
    fun getMuscleGroupsByDates(
        startDate: Long,
        endDate: Long,
    ): Flow<List<MusclesInDayTuple>>
}
