package kovp.trainhard.new_training_presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kovp.trainhard.new_training_presentation.select_new_exercise_type.ExerciseVs
import kovp.trainhard.new_training_presentation.select_new_exercise_type.ui.ExerciseItem
import kovp.trainhard.ui_theme.TrainHardTheme

@Preview
@Composable
private fun ExerciseItemComposablePreview(
    @PreviewParameter(ExerciseVsProvider::class) vs: ExerciseVs,
) {
    TrainHardTheme {
        ExerciseItem(item = vs, onItemClick = {})
    }
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
