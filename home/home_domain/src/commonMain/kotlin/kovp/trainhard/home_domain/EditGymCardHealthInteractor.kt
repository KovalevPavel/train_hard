package kovp.trainhard.home_domain

import kovp.trainhard.domain_storage.DataStoreWrapper

class EditGymCardHealthInteractor(
    private val prefs: DataStoreWrapper,
) {
    suspend operator fun invoke(startDate: Long?, endDate: Long?) {
        prefs.saveLong(PrefsConstants.START_TIMESTAMP_KEY, startDate)
        prefs.saveLong(PrefsConstants.EXPIRATION_TIMESTAMP_KEY, endDate)
    }
}
