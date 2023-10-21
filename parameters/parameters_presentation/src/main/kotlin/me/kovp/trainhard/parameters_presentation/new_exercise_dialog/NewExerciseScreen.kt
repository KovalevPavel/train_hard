package me.kovp.trainhard.parameters_presentation.new_exercise_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import me.kovp.components.R.string
import me.kovp.parameters_presentation.R
import me.kovp.trainhard.components.button.TrainButton
import me.kovp.trainhard.components.muscle_groups_cloud.MuscleGroupsCloud
import me.kovp.trainhard.components.text_field.TrainTextField
import me.kovp.trainhard.core_domain.Muscle
import me.kovp.trainhard.core_domain.MuscleGroup
import me.kovp.trainhard.core_domain.Muscles
import me.kovp.trainhard.navigation_api.navigation_styles.SlideFromBottomTransition
import me.kovp.trainhard.parameters_api.NewExerciseDialogScreen.RequestAction
import me.kovp.trainhard.parameters_presentation.data.ExerciseScreenArgument
import me.kovp.trainhard.parameters_presentation.new_exercise_dialog.NewExerciseScreenResult.Success.ScreenAction
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@Destination(style = SlideFromBottomTransition::class)
@Composable
fun NewExerciseScreen(
    argument: ExerciseScreenArgument? = null,
    requestAction: RequestAction,
    resultNavigator: ResultBackNavigator<NewExerciseScreenResult>,
) {
    val muscleList = Muscles.allMuscles.filter { it.id in argument?.muscleIds.orEmpty() }

    var currentQuery by remember { mutableStateOf(argument?.title.orEmpty()) }
    var selectedMuscleGroups: List<Muscle> by remember { mutableStateOf(muscleList) }

    Column(
        modifier = Modifier
            .background(color = themeColors.black)
            .fillMaxSize()
            .padding(all = 16.dp),
    ) {
        Text(
            modifier = Modifier
                .background(themeColors.black)
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            text = stringResource(id = R.string.new_exercise_screen_title),
            style = themeTypography.header1.copy(color = themeColors.lime)
        )
        TrainTextField(
            value = currentQuery,
            hint = stringResource(id = R.string.enter_exercise_hint),
            onValueChanged = { currentQuery = it },
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(MuscleGroup.values()) {
                MuscleGroupsCloud(
                    group = it,
                    initialList = muscleList,
                ) { muscle, isSelected ->
                    selectedMuscleGroups = if (isSelected) {
                        selectedMuscleGroups + muscle
                    } else {
                        selectedMuscleGroups - muscle
                    }
                }
            }
        }
        TrainButton(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            isEnabled = currentQuery.isNotEmpty() && selectedMuscleGroups.isNotEmpty(),
            label = stringResource(id = string.save),
        ) {
            resultNavigator.navigateBack(
                result = NewExerciseScreenResult.Success(
                    title = currentQuery,
                    muscleIds = selectedMuscleGroups.map(Muscle::id),
                    screenAction = when (requestAction) {
                        RequestAction.ADD -> ScreenAction.ADD
                        RequestAction.EDIT -> ScreenAction.EDIT
                    }
                )
            )
        }
    }
}
