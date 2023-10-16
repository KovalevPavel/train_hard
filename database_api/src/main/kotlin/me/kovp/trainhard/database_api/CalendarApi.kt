package me.kovp.trainhard.database_api

import kotlinx.coroutines.flow.Flow
import me.kovp.trainhard.core_domain.MuscleGroup

interface CalendarApi {
    fun getMuscleGroupsByDates(
        startDate: Long,
        endDate: Long,
    ): Flow<Map<Long, List<MuscleGroup>>>
}
