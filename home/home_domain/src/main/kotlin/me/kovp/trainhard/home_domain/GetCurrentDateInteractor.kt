package me.kovp.trainhard.home_domain

import java.util.Calendar

class GetCurrentDateInteractor {
    operator fun invoke(): Long = Calendar.getInstance()
        .timeInMillis
}
