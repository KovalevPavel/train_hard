package kovp.trainhard.parameters_presentation.exercise_parameters.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kovp.trainhard.parameters_presentation.exercise_parameters.presentation.ExerciseParametersAction
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParamsTopAppBar(
    title: String,
    action: String,
    handleAction: (ExerciseParametersAction) -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { handleAction(ExerciseParametersAction.OnBackClick) }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    tint = themeColors.white,
                    contentDescription = null,
                )
            }
        },
        title = {
            Text(text = title, style = themeTypography.header1, color = themeColors.white)
        },
        actions = {
            TextButton(onClick = { handleAction(ExerciseParametersAction.OnActionClick) }) {
                Text(text = action, color = themeColors.lime)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = themeColors.black),
    )
}
