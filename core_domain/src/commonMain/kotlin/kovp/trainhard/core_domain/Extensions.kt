@file:Suppress("unused")

package kovp.trainhard.core_domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

fun Int?.orZero() = this ?: 0
fun Float?.orZero() = this ?: 0
fun Boolean?.orFalse() = this ?: false
fun Boolean?.orTrue() = this ?: true
fun Long?.orZero(): Long = this ?: 0

fun <T : Any> MutableStateFlow<T>.update(newState: T) =
    this.compareAndSet(expect = this.value, update = newState)

fun Instant.toStartOfDay(): Long {
    return this.toLocalDateTime(TimeZone.currentSystemDefault()).date.toStartOfDay()
}

fun LocalDate.toStartOfDay(): Long {
    return this
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}
