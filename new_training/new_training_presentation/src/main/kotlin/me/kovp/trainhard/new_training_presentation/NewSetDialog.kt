package me.kovp.trainhard.new_training_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import me.kovp.trainhard.components.counter.Counter
import me.kovp.trainhard.components.counter.CounterValue
import me.kovp.trainhard.components.counter.CounterValue.Float
import me.kovp.trainhard.ui_theme.providers.themeColors

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun NewSetDialog(
    exerciseTitle: String,
    resultNavigator: ResultBackNavigator<CounterValue?>,
) {
    var valueFromDialog: CounterValue? = null

    Column(
        modifier = Modifier.background(themeColors.gray)
    ) {
        Text(text = exerciseTitle)
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(themeColors.gray),
            contentAlignment = Alignment.Center,
        ) {
            Counter(
                modifier = Modifier.padding(16.dp),
                initialValue = Float(50f),
                increment = Float(2.5f),
            ) {
                valueFromDialog = it
            }
        }
        Button(onClick = { resultNavigator.navigateBack(result = valueFromDialog) }) {
            Surface(
                color = themeColors.lime
            ) {
                Text(text = "Ok")
            }
        }
    }
}
