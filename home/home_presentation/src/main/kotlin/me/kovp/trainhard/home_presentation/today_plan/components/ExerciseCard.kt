package me.kovp.trainhard.home_presentation.today_plan.components

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
import me.kovp.home_presentation.R
import me.kovp.trainhard.home_presentation.today_plan.TodayPlan.TrainingDay.Exercise
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun ExerciseCard(exercise: Exercise) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
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
