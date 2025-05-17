package kovp.trainhard.components.counter

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

sealed interface CounterValue {
    val value: Number

    @Serializable
    data class Int(
        @Contextual
        override val value: Number,
    ) : CounterValue

    @Serializable
    data class Float(
        @Contextual
        override val value: Number,
    ) : CounterValue

    companion object {
        val unknownException = IllegalArgumentException("Unknown CounterValue type")
    }
}

inline fun <reified T: CounterValue> Number.toCounterValue(): T = when(T::class) {
    CounterValue.Int::class -> CounterValue.Int(this) as T
    CounterValue.Float::class -> CounterValue.Float(this) as T
    else -> throw CounterValue.unknownException
}

inline operator fun <reified T: CounterValue> T.plus(other: T): T = when (T::class) {
    CounterValue.Int::class -> CounterValue.Int(value = this.value.toInt() + other.value.toInt()) as T
    CounterValue.Float::class -> CounterValue.Float(value = this.value.toFloat() + other.value.toFloat()) as T
    else -> throw CounterValue.unknownException
}

inline operator fun <reified T: CounterValue> T.minus(other: T): T = when (T::class) {
    CounterValue.Int::class -> CounterValue.Int(value = this.value.toInt() - other.value.toInt()) as T
    CounterValue.Float::class -> CounterValue.Float(value = this.value.toFloat() - other.value.toFloat()) as T
    else -> throw CounterValue.unknownException
}
