package kovp.trainhard.database_api

import kotlinx.coroutines.flow.Flow
import kovp.trainhard.core_domain.MuscleGroup

interface CalendarApi {
    fun getMuscleGroupsByDates(
        startDate: Long,
        endDate: Long,
    ): Flow<Map<Long, List<MuscleGroup>>>
}
