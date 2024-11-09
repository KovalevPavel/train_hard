package kovp.trainhard.parameters_presentation.new_exercise_dialog

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.button.TrainButton
import kovp.trainhard.core_domain.MuscleGroup
import kovp.trainhard.parameters_presentation.R
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun NewExerciseScreen(
//    argument: ExerciseScreenArgument? = null,
//    requestAction: RequestAction,
//    resultNavigator: ResultBackNavigator<NewExerciseScreenResult>,
) {
//    val muscleList = Muscles.allMuscles.filter { it.id in argument?.muscleIds.orEmpty() }

//    var currentQuery by remember { mutableStateOf(argument?.title.orEmpty()) }
//    var selectedMuscleGroups: List<Muscle> by remember { mutableStateOf(muscleList) }

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
//        TrainTextField(
//            value = currentQuery,
//            hint = stringResource(id = R.string.enter_exercise_hint),
//            onValueChanged = { currentQuery = it },
//        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(MuscleGroup.values()) {
//                MuscleGroupsCloud(
//                    group = it,
//                    initialList = muscleList,
//                ) { muscle, isSelected ->
//                    selectedMuscleGroups = if (isSelected) {
//                        selectedMuscleGroups + muscle
//                    } else {
//                        selectedMuscleGroups - muscle
//                    }
//                }
            }
        }
        TrainButton(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
//            isEnabled = currentQuery.isNotEmpty() && selectedMuscleGroups.isNotEmpty(),
            label = stringResource(id = kovp.trainhard.design_system.R.string.save),
        ) {
//            resultNavigator.navigateBack(
//                result = NewExerciseScreenResult.Success(
//                    title = currentQuery,
//                    muscleIds = selectedMuscleGroups.map(Muscle::id),
//                    screenAction = when (requestAction) {
//                        RequestAction.ADD -> ScreenAction.ADD
//                        RequestAction.EDIT -> ScreenAction.EDIT
//                    }
//                )
//            )
        }
    }
}
