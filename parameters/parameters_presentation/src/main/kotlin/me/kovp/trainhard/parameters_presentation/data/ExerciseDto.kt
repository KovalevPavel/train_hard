package me.kovp.trainhard.parameters_presentation.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseDto(
    val title: String,
): Parcelable
