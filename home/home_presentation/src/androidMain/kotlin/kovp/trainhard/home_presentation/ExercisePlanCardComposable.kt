package kovp.trainhard.home_presentation

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.persistentListOf
import kovp.trainhard.home_presentation.components.ExercisePlanCardComposable
import kovp.trainhard.home_presentation.home.presentation.TodayPlan.TrainingDay.ExerciseCardVs
import kovp.trainhard.ui_theme.TrainHardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun ExercisePlanCardPreview() {
    TrainHardTheme {
        ExercisePlanCardComposable(
            exercise = ExerciseCardVs(
                id = "",
                title = "Становая тяга",
                sets = persistentListOf(
                    "180kg: 3x6",
                    "180kg: 3x6",
                    "180kg: 3x6",
                    "180kg: 3x6",
                ),
                muscleGroups = "спина",
            )
        )
    }
}
