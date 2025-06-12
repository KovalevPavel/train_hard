package kovp.trainhard.parameters_presentation.exercise_parameters.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersState
import kovp.trainhard.ui_theme.TrainHardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

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
