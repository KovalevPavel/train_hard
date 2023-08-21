package me.kovp.trainhard.home_domain.today_plan

import java.util.Calendar

class GetCurrentDateInteractor {
    operator fun invoke(): Long = Calendar.getInstance()
        .timeInMillis
}
