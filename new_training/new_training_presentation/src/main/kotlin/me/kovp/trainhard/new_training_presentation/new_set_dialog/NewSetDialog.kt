package me.kovp.trainhard.new_training_presentation.new_set_dialog

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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import me.kovp.new_training_presentation.R
import me.kovp.trainhard.components.button.TrainButton
import me.kovp.trainhard.components.counter.Counter
import me.kovp.trainhard.components.counter.CounterValue
import me.kovp.trainhard.new_training_api.NewSetDialogScreen.RequestAction
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun NewSetDialog(
    setId: Long,
    repsId: Long,
    exerciseTitle: String,
    initWeight: Float,
    initReps: Int,
    requestAction: RequestAction,
    resultNavigator: ResultBackNavigator<NewSetDialogResult>,
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
        Counter(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.weight_label),
            initialValue = CounterValue.Float(initWeight),
            increment = CounterValue.Float(2.5f),
        ) {
            selectedWeight = it
        }
        Counter(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.reps_label),
            initialValue = CounterValue.Int(initReps),
            increment = CounterValue.Float(1f),
        ) {
            selectedReps = it
        }

        TrainButton(
            modifier = Modifier.align(Alignment.End),
            label = stringResource(id = me.kovp.components.R.string.save),
        ) {
            resultNavigator.navigateBack(
                result = NewSetDialogResult.Success(
                    id = setId,
                    setId = repsId,
                    exerciseTitle = exerciseTitle,
                    weight = selectedWeight.value.toFloat(),
                    reps = selectedReps.value.toInt(),
                    resultAction = when (requestAction) {
                        RequestAction.ADD -> NewSetDialogResult.DialogAction.ADD_NEW
                        RequestAction.EDIT -> NewSetDialogResult.DialogAction.EDIT_CURRENT
                    },
                )
            )
        }
    }
}
