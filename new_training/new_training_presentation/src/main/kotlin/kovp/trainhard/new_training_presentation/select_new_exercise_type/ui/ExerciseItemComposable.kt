package kovp.trainhard.new_training_presentation.select_new_exercise_type.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import kovp.trainhard.new_training_presentation.select_new_exercise_type.ExerciseVs
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun ExerciseItem(
    modifier: Modifier = Modifier,
    item: ExerciseVs,
    onItemClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(16),
        color = themeColors.black,
        border = BorderStroke(width = 1.dp, color = themeColors.gray),
        onClick = onItemClick,
    ) {
        Column(
            modifier = modifier
                .minimumInteractiveComponentSize()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = themeTypography.header2,
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(.25f)
                    .padding(vertical = 4.dp),
                color = themeColors.white,
            )
            Text(
                text = item.muscles,
                style = themeTypography.body1,
                color = themeColors.white.copy(alpha = .7f)
            )
        }
    }
}

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
