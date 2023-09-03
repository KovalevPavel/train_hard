package me.kovp.trainhard.core_design

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.kovp.core_design.R
import me.kovp.trainhard.core_domain.MuscleGroup

@Composable
fun mapMuscleGroupTitle(title: String): String {
    val stringRes = when (MuscleGroup.getGroupById(title)) {
        MuscleGroup.Legs.QUADRICEPS -> R.string.legs_quad
        MuscleGroup.Legs.BICEPS -> R.string.legs_bic
        MuscleGroup.Legs.CALVES -> R.string.legs_calves
        MuscleGroup.Chest.UPPER_CHEST -> R.string.chest_upper
        MuscleGroup.Chest.LOWER_CHEST -> R.string.chest_lower
        MuscleGroup.Deltas.ANTERIOR -> R.string.deltas_ant
        MuscleGroup.Deltas.MIDDLE -> R.string.deltas_mid
        MuscleGroup.Deltas.POSTERIOR -> R.string.deltas_post
        MuscleGroup.Back.LATS -> R.string.back_lats
        MuscleGroup.Back.LOIN -> R.string.back_loin
        MuscleGroup.Back.TRAPEZIUS -> R.string.back_trapezius
        MuscleGroup.Arms.BICEPS -> R.string.arms_bic
        MuscleGroup.Arms.TRICEPS -> R.string.arms_tric
        MuscleGroup.Arms.FOREARMS -> R.string.arms_fore
        MuscleGroup.Abs -> R.string.abs_
        else -> null
    }

    return stringRes?.let { stringResource(id = stringRes) }.orEmpty()
}

@Composable
fun <T : Any> List<T>.joinToStringComposable(
    separator: CharSequence = ", ",
    transform: (@Composable (T) -> CharSequence)? = null
): String {
    val elements = this.map { transform?.let { it1 -> it1(it) } }

    return elements.joinToString(separator)
}
