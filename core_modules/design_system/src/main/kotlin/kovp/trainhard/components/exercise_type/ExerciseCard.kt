package kovp.trainhard.components.exercise_type

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.GlowContainer
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    card: ExerciseCardVs,
    onCardClick: (ExerciseCardVs) -> Unit,
    onRemoveClick: (ExerciseCardVs) -> Unit,
) {
    var visible by remember { mutableStateOf(true) }

    GlowContainer(modifier = modifier) {
        Surface(
            color = themeColors.gray,
            onClick = { onCardClick(card) },
        ) {

            Column(
                modifier = Modifier.padding(all = 16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = card.title,
                        style = themeTypography.header1,
                    )
                    IconButton(
                        onClick = {
                            visible = false
                            onRemoveClick(card)
                        }
                    ) {
                        Image(
                            imageVector = Filled.Clear,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = themeColors.white),
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                )
                Text(text = card.muscles, style = themeTypography.body2)
            }
        }
    }
}

@Preview
@Composable
private fun ExerciseCardPreview(
    @PreviewParameter(ExerciseCardProvider::class) card: ExerciseCardVs,
) {
    TrainHardTheme {
        ExerciseCard(card = card, onCardClick = {}, onRemoveClick = {})
    }
}

private class ExerciseCardProvider : PreviewParameterProvider<ExerciseCardVs> {
    override val values: Sequence<ExerciseCardVs>
        get() = sequenceOf(
            ExerciseCardVs(
                title = "",
                muscles = "",
            ),
            ExerciseCardVs(
                title = "Full body",
                muscles = "Верх грудных, середина грудных, низ грудных, квадрицепс, бицепс бедра",
            ),
            ExerciseCardVs(
                title = "Full body ".repeat(5).trimIndent(),
                muscles = "Верх грудных, середина грудных, низ грудных, квадрицепс, бицепс бедра",
            ),
        )
}
