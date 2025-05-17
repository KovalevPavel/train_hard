package kovp.trainhard.parameters_presentation.exercise_parameters.ui

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
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kovp.trainhard.components.muscle_groups_cloud.TrainChip
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersState
import kovp.trainhard.ui_theme.providers.themeTypography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MuscleGroupsCloud(
    modifier: Modifier = Modifier,
    title: String,
    initialList: ImmutableList<ExerciseParametersState.MuscleVs>,
    onChipClick: (muscleId: String, isChecked: Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = themeTypography.body1,
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            initialList.forEach {
                TrainChip(
                    id = it.id,
                    title = it.title,
                    selected = it.isSelected,
                    onChipClick = onChipClick,
                )
            }
        }
    }
}
