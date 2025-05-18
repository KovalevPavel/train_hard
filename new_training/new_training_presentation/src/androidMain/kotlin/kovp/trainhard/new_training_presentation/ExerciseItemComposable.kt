package kovp.trainhard.new_training_presentation

import androidx.compose.runtime.Composable
import kovp.trainhard.new_training_presentation.select_new_exercise_type.ExerciseVs
import kovp.trainhard.new_training_presentation.select_new_exercise_type.ui.ExerciseItem
import kovp.trainhard.ui_theme.TrainHardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Preview
@Composable
private fun ExerciseItemComposablePreview(
    @PreviewParameter(ExerciseVsProvider::class) vs: ExerciseVs,
) {
    TrainHardTheme {
        ExerciseItem(item = vs, onItemClick = {})
    }

    rememberKoinModules()
}

private class ExerciseVsProvider : PreviewParameterProvider<ExerciseVs> {
    override val values: Sequence<ExerciseVs>
        get() = sequenceOf(
            ExerciseVs(
                title = "Приседания",
                muscles = "мышцы, ".repeat(10),
            ),
        )
}
