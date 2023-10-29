package kovp.trainhard.home_domain

import kovp.trainhard.domain_storage.SharedPreferencesWrapper

class EditGymCardHealthInteractor(
    private val prefs: SharedPreferencesWrapper,
) {
    suspend operator fun invoke(startDate: Long?, endDate: Long?) {
        prefs.saveLong(PrefsConstants.START_TIMESTAMP_KEY, startDate)
        prefs.saveLong(PrefsConstants.EXPIRATION_TIMESTAMP_KEY, endDate)
    }
}
