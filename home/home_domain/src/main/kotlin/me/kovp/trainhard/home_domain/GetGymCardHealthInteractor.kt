package me.kovp.trainhard.home_domain

import me.kovp.trainhard.domain_storage.SharedPreferencesWrapper

class GetGymCardHealthInteractor(
    private val prefs: SharedPreferencesWrapper,
) {
    suspend operator fun invoke(): Pair<Long, Long>? {
        val startTimestamp = prefs.getLong(PrefsConstants.START_TIMESTAMP_KEY)
        val expirationTimestamp = prefs.getLong(PrefsConstants.EXPIRATION_TIMESTAMP_KEY)

        return if (startTimestamp == null || expirationTimestamp == null) {
            null
        } else {
            startTimestamp to expirationTimestamp
        }
    }
}
