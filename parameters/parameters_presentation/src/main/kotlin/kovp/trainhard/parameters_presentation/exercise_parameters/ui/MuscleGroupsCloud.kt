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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kovp.trainhard.components.muscle_groups_cloud.TrainChip
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersState
import kovp.trainhard.ui_theme.TrainHardTheme
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

@Preview
@Composable
private fun MuscleGroupsCloudPreview() {
    var list: ImmutableList<ExerciseParametersState.MuscleVs> by remember {
        mutableStateOf(
            List(5) {
                ExerciseParametersState.MuscleVs(
                    id = it.toString(),
                    title = "Muscle $it",
                    isSelected = false,
                )
            }
                .toImmutableList()
        )
    }

    TrainHardTheme {
        MuscleGroupsCloud(title = "title",
            initialList = list.toImmutableList(),
            onChipClick = { id, isSelected ->
                list = list.map { i ->
                    if (i.id == id) {
                        i.copy(isSelected = isSelected)
                    } else {
                        i
                    }
                }
                    .toImmutableList()
            }
        )
    }
}
