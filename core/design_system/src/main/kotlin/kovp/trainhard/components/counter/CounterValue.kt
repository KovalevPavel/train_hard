package kovp.trainhard.components.counter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kovp.trainhard.components.counter.CounterValue.Float

sealed interface CounterValue: Parcelable {
    val value: Number

    @Parcelize
    data class Int(
        override val value: kotlin.Int,
    ) : CounterValue

    @Parcelize
    data class Float(
        override val value: kotlin.Float,
    ) : CounterValue
}

fun Number.toCounterValue() = when(this) {
    is Int -> CounterValue.Int(this)
    else -> Float(this.toFloat())
}

operator fun CounterValue.plus(other: CounterValue): CounterValue = when (this) {
    is CounterValue.Int -> this.copy(value = this.value + other.value.toInt())
    is Float -> this.copy(value = this.value + other.value.toFloat())
}

operator fun CounterValue.minus(other: CounterValue): CounterValue = when (this) {
    is CounterValue.Int -> this.copy(value = this.value - other.value.toInt())
    is Float -> this.copy(value = this.value - other.value.toFloat())
}
