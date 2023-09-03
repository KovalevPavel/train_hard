package me.kovp.trainhard.components.train_card

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SetCardDto(
    val exerciseTitle: String,
    val sets: List<String>,
    val muscleGroups: List<String>,
) : Parcelable
