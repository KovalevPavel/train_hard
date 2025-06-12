package kovp.trainhard.parameters_presentation.exercise_parameters.ui

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import kovp.trainhard.components.TopBar
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersAction
import kovp.trainhard.ui_theme.providers.themeColors

@Composable
fun ParamsTopAppBar(
    title: String,
    action: String,
    handleAction: (ExerciseParametersAction) -> Unit,
) {
    TopBar(
        header = title,
        onBackClick = { handleAction(ExerciseParametersAction.OnBackClick) },
        actions = {
            TextButton(onClick = { handleAction(ExerciseParametersAction.OnActionClick) }) {
                Text(text = action, color = themeColors.lime)
            }
        },
    )
}
