package kovp.trainhard.components.train_card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import kovp.trainhard.design_system.R
import kovp.trainhard.components.button.TrainButton
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun CompletedExerciseCard(
    modifier: Modifier = Modifier,
    card: CompletedExerciseCardVs,
    onAddSetClick: () -> Unit,
    onRemoveSetClick: (Int) -> Unit,
    onEditSetClick: (Int) -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = themeColors.gray,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
        ) {
            Text(
                text = card.exerciseTitle,
                style = themeTypography.header1,
            )
            Column(
                modifier = Modifier.animateContentSize()
            ) {
                card.sets.forEachIndexed { index, (w, r) ->
                    SetElement(
                        text = stringResource(id = R.string.weight_reps_template, w, r),
                        isEditable = true,
                        onRemoveClick = { onRemoveSetClick(index) },
                        onSetClick = { onEditSetClick(index) }
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.muscle_groups, card.muscles),
                style = themeTypography.body2,
            )

            TrainButton(
                modifier = Modifier
                    .align(Alignment.End),
                label = stringResource(id = R.string.add_set),
                onClick = onAddSetClick,
            )
        }
    }
}

@Preview
@Composable
private fun CompletedExerciseCardPreview(
    @PreviewParameter(CompletedExerciseCardDtoProvider::class) item: CompletedExerciseCardVs,
) {
    TrainHardTheme {
        CompletedExerciseCard(
            card = item,
            onAddSetClick = {},
            onEditSetClick = {},
            onRemoveSetClick = {},
        )
    }
}

private class CompletedExerciseCardDtoProvider :
    PreviewParameterProvider<CompletedExerciseCardVs> {
    override val values: Sequence<CompletedExerciseCardVs>
        get() = sequenceOf(
            CompletedExerciseCardVs(
                setId = 0,
                timestamp = 0,
                exerciseTitle = "Подъемы ног",
                sets = listOf(),
                muscles = "",
            ),
            CompletedExerciseCardVs(
                setId = 0,
                timestamp = 0,
                exerciseTitle = "Подъемы ног",
                sets = listOf(
                    0f to 5,
                ),
                muscles = "мышцы, ".repeat(10),
            ),
            CompletedExerciseCardVs(
                setId = 0,
                timestamp = 0,
                exerciseTitle = "Подъемы ног ".repeat(5).trimIndent(),
                sets = listOf(
                    0f to 5,
                    0f to 5,
                    0f to 5,
                    0f to 5,
                ),
                muscles = "пресс",
            ),
            CompletedExerciseCardVs(
                setId = 0,
                timestamp = 0,
                exerciseTitle = "Подъемы ног",
                sets = listOf(
                    0f to 5,
                ),
                muscles = "мышцы, ".repeat(10),
            ),
        )
}
