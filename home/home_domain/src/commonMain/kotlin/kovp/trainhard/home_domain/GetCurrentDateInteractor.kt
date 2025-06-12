package kovp.trainhard.home_domain

import kotlinx.datetime.Clock

class GetCurrentDateInteractor {
    operator fun invoke(): Long = Clock.System.now().toEpochMilliseconds()
}
