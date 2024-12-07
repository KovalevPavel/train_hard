package kovp.trainhard.home_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kovp.trainhard.home_presentation.R
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.TrainingDay.Exercise
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun ExerciseCardComposable(exercise: Exercise) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = themeColors.gray,
                shape = RoundedCornerShape(16.dp),
            ),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = exercise.title,
            style = themeTypography.body1,
        )
        exercise.sets.forEach { set ->
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = set,
                style = themeTypography.body2,
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(id = R.string.muscle_groups, exercise.muscleGroups),
            style = themeTypography.body2,
        )
    }
}
