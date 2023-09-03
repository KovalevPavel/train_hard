package me.kovp.trainhard.components.train_card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.kovp.components.R
import me.kovp.trainhard.components.button.TrainButton
import me.kovp.trainhard.core_design.joinToStringComposable
import me.kovp.trainhard.core_design.mapMuscleGroupTitle
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun SetCard(
    modifier: Modifier = Modifier,
    card: SetCardDto,
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
                card.sets.forEachIndexed { index, set ->
                    SetElement(
                        text = set,
                        isEditable = true,
                        onRemoveClick = { onRemoveSetClick(index) },
                        onSetClick = { onEditSetClick(index) }
                    )
                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            val groups = card.muscleGroups
                .joinToStringComposable { mapMuscleGroupTitle(title = it) }

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
