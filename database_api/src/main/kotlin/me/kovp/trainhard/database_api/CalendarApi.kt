package me.kovp.trainhard.database_api

import me.kovp.trainhard.core_domain.MuscleGroup

interface CalendarApi {
    suspend fun getMuscleGroupsByDates(startDate: Long, endDate: Long): Map<Long, List<MuscleGroup>>
}
