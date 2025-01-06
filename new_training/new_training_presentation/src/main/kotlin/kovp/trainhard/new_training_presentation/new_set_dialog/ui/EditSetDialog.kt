package kovp.trainhard.new_training_presentation.new_set_dialog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.button.TrainButton
import kovp.trainhard.components.counter.CounterValue
import kovp.trainhard.components.counter.TrainCounter
import kovp.trainhard.new_training_presentation.R
import kovp.trainhard.new_training_presentation.new_set_dialog.EditSetDialogResult
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

private const val DEFAULT_COUNTER_WEIGHT_INCREMENT = 2.5f

@Composable
fun EditSetDialog(
    setId: Long,
    repsId: Long,
    exerciseTitle: String,
    initWeight: Float,
    initReps: Int,
    onApplyClick: (EditSetDialogResult.Success) -> Unit,
) {
    var selectedWeight: CounterValue by remember {
        mutableStateOf(CounterValue.Float(initWeight))
    }

    var selectedReps: CounterValue by remember {
        mutableStateOf(CounterValue.Int(initReps))
    }

    Column(
        modifier = Modifier
            .background(
                color = themeColors.gray,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = exerciseTitle, style = themeTypography.header1)
        TrainCounter(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.weight_label),
            initialValue = CounterValue.Float(initWeight),
            increment = CounterValue.Float(DEFAULT_COUNTER_WEIGHT_INCREMENT),
        ) {
            selectedWeight = it
        }
        TrainCounter(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.reps_label),
            initialValue = CounterValue.Int(initReps),
            increment = CounterValue.Float(1f),
        ) {
            selectedReps = it
        }

        TrainButton(
            modifier = Modifier.align(Alignment.End),
            label = stringResource(id = kovp.trainhard.design_system.R.string.save),
            onClick = {
                onApplyClick(
                    EditSetDialogResult.Success(
                        id = setId,
                        setId = repsId,
                        exerciseTitle = exerciseTitle,
                        weight = selectedWeight.value.toFloat(),
                        reps = selectedReps.value.toInt(),
                    )
                )
            }
        )
    }
}
