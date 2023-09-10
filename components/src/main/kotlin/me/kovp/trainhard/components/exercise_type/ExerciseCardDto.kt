package me.kovp.trainhard.components.exercise_type

import me.kovp.trainhard.core_domain.Muscle

data class ExerciseCardDto(
    val title: String,
    val muscles: List<Muscle>,
)
