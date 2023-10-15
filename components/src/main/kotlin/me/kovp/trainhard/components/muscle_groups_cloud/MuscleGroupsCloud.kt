package me.kovp.trainhard.components.muscle_groups_cloud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.kovp.components.R
import me.kovp.trainhard.core_domain.Muscle
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.core_domain.Muscles
import me.kovp.trainhard.ui_theme.providers.themeTypography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MuscleGroupsCloud(
    modifier: Modifier = Modifier,
    group: MuscleGroup,
    initialList: List<Muscle>,
    onChipClick: (muscle: Muscle, isChecked: Boolean) -> Unit,
) {
    val muscles = Muscles.getMusclesByGroup(group)

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = group.getMuscleGroupTitle(),
            style = themeTypography.body1,
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            muscles.forEach {
                TrainChip(muscle = it, selected = it in initialList, onChipClick = onChipClick)
            }
        }
    }
}

@Composable
fun MuscleGroup.getMuscleGroupTitle(): String {
    val stringResId = when (this) {
        MuscleGroup.CHEST -> R.string.group_chest
        MuscleGroup.BACK -> R.string.group_back
        MuscleGroup.LEGS -> R.string.group_legs
        MuscleGroup.DELTOIDS -> R.string.group_deltoids
        MuscleGroup.ARMS -> R.string.group_arms
        MuscleGroup.ABS -> R.string.group_abs
    }

    return stringResource(id = stringResId)
}
