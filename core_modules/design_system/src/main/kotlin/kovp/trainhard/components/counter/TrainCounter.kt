package kovp.trainhard.components.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kovp.trainhard.components.counter.CounterValue.Float
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
inline fun <reified T: CounterValue> TrainCounter(
    modifier: Modifier = Modifier,
    label: String = "",
    initialValue: T,
    increment: T,
    crossinline onValueChanged: (T) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = themeTypography.body1
        )
        Counter(
            initialValue = initialValue,
            increment = increment,
            onValueChanged = onValueChanged,
        )
    }
}
