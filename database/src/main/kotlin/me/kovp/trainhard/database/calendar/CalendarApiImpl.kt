package me.kovp.trainhard.database.calendar

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.core_domain.Muscles
import me.kovp.trainhard.database.dao.CalendarDao
import me.kovp.trainhard.database_api.CalendarApi

internal class CalendarApiImpl(
    private val calendarDao: CalendarDao,
) : CalendarApi {
    override fun getMuscleGroupsByDates(
        startDate: Long,
        endDate: Long
    ): Flow<Map<Long, List<MuscleGroup>>> {
        val exercisesIdsInDatesFlow = calendarDao.getMuscleGroupsByDates(
            startDate = startDate,
            endDate = endDate,
        )

        return exercisesIdsInDatesFlow.map { tupleList ->
            tupleList.mapNotNull {
                val timestamp = it.timestamp ?: return@mapNotNull null
                timestamp to it.muscles?.split(EXERCISES_IDS_DELIMITER).orEmpty()
            }
                .groupBy { it.first }
                .mapValues { (_, list) ->
                    list.asSequence()
                        .map { it.second }
                        .flatten()
                        .mapNotNull { Muscles.getMuscleGroup(it) ?: return@mapNotNull null }
                        .toList()
                        .distinct()
                }
        }
    }

    companion object {
        private const val EXERCISES_IDS_DELIMITER = ","
    }
}
