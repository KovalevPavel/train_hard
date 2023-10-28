package kovp.trainhard.core_design

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kovp.trainhard.core_domain.Muscles

@Composable
fun mapMuscleTitle(muscleId: String): String {
    val stringRes = when (muscleId) {
        Muscles.quadriceps.id -> R.string.legs_quad
        Muscles.hamstrings.id -> R.string.legs_bic
        Muscles.calves.id -> R.string.legs_calves
        Muscles.upperChest.id -> R.string.chest_upper
        Muscles.lowerChest.id -> R.string.chest_lower
        Muscles.deltoidsAnt.id -> R.string.deltas_ant
        Muscles.deltoidsMid.id -> R.string.deltas_mid
        Muscles.deltoidsPost.id -> R.string.deltas_post
        Muscles.lats.id -> R.string.back_lats
        Muscles.loin.id -> R.string.back_loin
        Muscles.trapezius.id -> R.string.back_trapezius
        Muscles.armsBiceps.id -> R.string.arms_bic
        Muscles.armsTriceps.id -> R.string.arms_tric
        Muscles.armsForearms.id -> R.string.arms_fore
        Muscles.abs.id -> R.string.abs_
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
