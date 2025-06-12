package kovp.trainhard.home_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.GlowContainer
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.TrainingDay.ExerciseCardVs
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.jetbrains.compose.resources.stringResource
import trainhard.home_presentation.generated.resources.Res
import trainhard.home_presentation.generated.resources.muscle_groups

@Composable
fun ExercisePlanCardComposable(exercise: ExerciseCardVs) {
    GlowContainer {
        Box(
            modifier = Modifier
                .background(color = themeColors.gray)
                .padding(horizontal = 16.dp),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = exercise.title,
                    style = themeTypography.body1,
                )
                exercise.sets.forEach { set ->
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = set,
                        style = themeTypography.body2,
                    )
                }
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(resource = Res.string.muscle_groups, exercise.muscleGroups),
                    style = themeTypography.body2,
                )
            }
        }
    }
}
