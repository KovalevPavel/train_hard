package me.kovp.trainhard.components.train_card

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompletedExerciseCardDto(
    val setId: Long,
    val timestamp: Long,
    val exerciseTitle: String,
    val sets: List<Pair<Float, Int>>,
    val muscles: List<String>,
) : Parcelable
