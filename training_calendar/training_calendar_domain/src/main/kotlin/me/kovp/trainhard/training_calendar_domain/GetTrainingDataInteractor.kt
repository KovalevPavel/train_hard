package me.kovp.trainhard.training_calendar_domain

import me.kovp.trainhard.database_api.CalendarApi

class GetTrainingDataInteractor(
    private val calendarApi: CalendarApi,
) {
    suspend operator fun invoke(
        startDate: Long,
        endDate: Long,
    ) = calendarApi.getMuscleGroupsByDates(startDate, endDate)
}
