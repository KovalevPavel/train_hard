package kovp.trainhard.training_calendar_domain

import kovp.trainhard.database_api.CalendarApi

class GetTrainingDataInteractor(
    private val calendarApi: CalendarApi,
) {
    operator fun invoke(
        startDate: Long,
        endDate: Long,
    ) = calendarApi.getMuscleGroupsByDates(startDate, endDate)
}
