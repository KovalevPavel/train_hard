package kovp.trainhard.components.exercise_type

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import kovp.trainhard.core_design.joinToStringComposable
import kovp.trainhard.core_design.mapMuscleTitle
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    card: ExerciseCardDto,
    onCardClick: (ExerciseCardDto) -> Unit,
    onRemoveClick: (ExerciseCardDto) -> Unit,
) {
    var visible by remember { mutableStateOf(true) }

    val list = card.muscles
        .joinToStringComposable(", ") {
            mapMuscleTitle(muscleId = it.id)
        }
        .replaceFirstChar { it.uppercaseChar() }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = themeColors.gray,
        shape = RoundedCornerShape(16.dp),
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
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp),
            )
            Text(text = list, style = themeTypography.body2)
        }
    }
}
