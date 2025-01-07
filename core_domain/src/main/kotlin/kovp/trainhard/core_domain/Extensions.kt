@file:Suppress("unused")

package kovp.trainhard.core_domain

import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.ZoneId

fun Int?.orZero() = this ?: 0
fun Float?.orZero() = this ?: 0
fun Boolean?.orFalse() = this ?: false
fun Long?.orZero(): Long = this ?: 0

fun <T : Any> MutableStateFlow<T>.update(newState: T) =
    this.compareAndSet(expect = this.value, update = newState)

fun LocalDate.toStartOfDay(): Long {
    return this
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}
