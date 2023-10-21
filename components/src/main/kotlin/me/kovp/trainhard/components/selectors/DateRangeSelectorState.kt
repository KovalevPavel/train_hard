package me.kovp.trainhard.components.selectors

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateRangeSelectorState(
    val startTimestamp: Long?,
    val endTimestamp: Long?,
) : Parcelable
