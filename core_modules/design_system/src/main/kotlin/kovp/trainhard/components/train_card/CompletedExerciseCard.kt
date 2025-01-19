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
import kovp.trainhard.components.joinToStringComposable
import kovp.trainhard.components.mapMuscleTitle
import kovp.trainhard.core_domain.Muscle
import kovp.trainhard.core_domain.Muscles
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun CompletedExerciseCard(
    modifier: Modifier = Modifier,
    card: CompletedExerciseCardDto,
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
            val groups = card.muscles
                .joinToStringComposable { mapMuscleTitle(muscleId = it) }

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.muscle_groups, groups),
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
    @PreviewParameter(CompletedExerciseCardDtoProvider::class) item: CompletedExerciseCardDto,
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
    PreviewParameterProvider<CompletedExerciseCardDto> {
    override val values: Sequence<CompletedExerciseCardDto>
        get() = sequenceOf(
            CompletedExerciseCardDto(
                setId = 0,
                timestamp = 0,
                exerciseTitle = "Подъемы ног",
                sets = listOf(),
                muscles = listOf(),
            ),
            CompletedExerciseCardDto(
                setId = 0,
                timestamp = 0,
                exerciseTitle = "Подъемы ног",
                sets = listOf(
                    0f to 5,
                ),
                muscles = listOf(Muscles.abs.id),
            ),
            CompletedExerciseCardDto(
                setId = 0,
                timestamp = 0,
                exerciseTitle = "Подъемы ног ".repeat(5).trimIndent(),
                sets = listOf(
                    0f to 5,
                    0f to 5,
                    0f to 5,
                    0f to 5,
                ),
                muscles = listOf(Muscles.abs.id),
            ),
            CompletedExerciseCardDto(
                setId = 0,
                timestamp = 0,
                exerciseTitle = "Подъемы ног",
                sets = listOf(
                    0f to 5,
                ),
                muscles = Muscles.allMuscles.map(Muscle::id),
            ),
        )
}
