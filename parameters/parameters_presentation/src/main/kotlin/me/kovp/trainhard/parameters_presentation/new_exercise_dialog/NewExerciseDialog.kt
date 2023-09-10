package me.kovp.trainhard.parameters_presentation.new_exercise_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle.BottomSheet
import me.kovp.components.R.string
import me.kovp.parameters_presentation.R
import me.kovp.trainhard.components.button.TrainButton
import me.kovp.trainhard.components.muscle_groups_cloud.MuscleGroupsCloud
import me.kovp.trainhard.components.text_field.TrainTextField
import me.kovp.trainhard.core_domain.Muscle
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.parameters_api.NewExerciseDialogScreen.RequestAction
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseDialogResult.Success.DialogAction
import me.kovp.trainhard.ui_theme.providers.themeColors

@Destination(style = BottomSheet::class)
@Composable
fun NewExerciseDialog(
    requestAction: RequestAction,
    resultNavigator: ResultBackNavigator<NewExerciseDialogResult>,
) {
    var currentQuery by remember { mutableStateOf("") }
    var selectedMuscleGroups: List<Muscle> by remember { mutableStateOf(listOf()) }

    Column(
        modifier = Modifier
            .background(
                color = themeColors.gray,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TrainTextField(
            value = currentQuery,
            hint = stringResource(id = R.string.enter_exercise_hint),
            onValueChanged = { currentQuery = it },
        )
        MuscleGroup.values().forEach {
            MuscleGroupsCloud(group = it) { muscle, isSelected ->
                selectedMuscleGroups = if (isSelected) {
                    selectedMuscleGroups + muscle
                } else {
                    selectedMuscleGroups - muscle
                }
            }
        }
        TrainButton(
            modifier = Modifier.align(Alignment.End),
            isEnabled = currentQuery.isNotEmpty() && selectedMuscleGroups.isNotEmpty(),
            label = stringResource(id = string.save),
        ) {
            resultNavigator.navigateBack(
                result = NewExerciseDialogResult.Success(
                    title = currentQuery,
                    muscleIds = selectedMuscleGroups.map(Muscle::id),
                    dialogAction = when (requestAction) {
                        RequestAction.ADD -> DialogAction.ADD
                        RequestAction.EDIT -> DialogAction.EDIT
                    }
                )
            )
        }
    }
}
