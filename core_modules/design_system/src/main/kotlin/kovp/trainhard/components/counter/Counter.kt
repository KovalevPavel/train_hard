package kovp.trainhard.components.counter

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kovp.trainhard.core_domain.orZero
import kovp.trainhard.design_system.R
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Suppress("LongMethod")
@Composable
inline fun <reified T: CounterValue> Counter(
    modifier: Modifier = Modifier,
    initialValue: T,
    increment: T,
    crossinline onValueChanged: (T) -> Unit,
) {
    var currentValue by remember { mutableStateOf(initialValue) }
    var currentString by remember { mutableStateOf(initialValue.value.toString()) }

    val regex = remember {
        when (T::class) {
            CounterValue.Int::class -> "\\d{0,3}"
            CounterValue.Float::class -> "\\d{0,3}?\\.?\\d{0,2}"
            else -> ""
        }
            .toRegex()
    }

    Row(modifier = modifier) {
        CounterButton(
            iconRes = R.drawable.ic_minus,
            buttonType = ButtonType.Remove,
        ) {
            val newValue = (currentValue - increment).value
                .let {
                    when (initialValue) {
                        is CounterValue.Int -> it.toInt().coerceAtLeast(0)
                        else -> it.toFloat().coerceAtLeast(0f)
                    }
                }
                .toCounterValue<T>()

            currentValue = newValue
            currentString = currentValue.value.toString()

            onValueChanged(currentValue)
        }
        CounterTextField(
            modifier = Modifier
                .height(40.dp)
                .width(70.dp)
                .background(color = themeColors.white)
                .padding(horizontal = 8.dp),
            currentString = currentString,
        ) { rawString ->
            val newString = rawString.takeIf { it.matches(regex) } ?: currentString
            val newValue = when (initialValue) {
                is CounterValue.Int -> {
                    newString.toIntOrNull()?.coerceAtLeast(0).orZero()
                }

                else -> {
                    newString.toFloatOrNull()?.coerceAtLeast(0f).orZero() as Number
                }
            }
                .toCounterValue<T>()
            if (newString == currentString) return@CounterTextField

            currentValue = newValue
            currentString = newString
            onValueChanged(currentValue)
        }
        CounterButton(
            iconRes = R.drawable.ic_plus,
            buttonType = ButtonType.Add,
        ) {
            val newValue = (currentValue + increment).value
                .let {
                    when (initialValue) {
                        is CounterValue.Int -> it.toInt()
                        else -> it.toFloat()
                    }
                }
                .toCounterValue<T>()

            currentValue = newValue
            currentString = currentValue.value.toString()
            onValueChanged(currentValue)
        }
    }
}

@Composable
fun CounterButton(
    @DrawableRes iconRes: Int,
    buttonType: ButtonType,
    onValueChange: () -> Unit
) {
    Surface(
        modifier = Modifier.size(40.dp),
        color = themeColors.lime,
        shape = RoundedCornerShape(
            topStart = buttonType.topStart,
            topEnd = buttonType.topEnd,
            bottomEnd = buttonType.bottomEnd,
            bottomStart = buttonType.bottomStart,
        ),
        onClick = onValueChange,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = iconRes),
                contentDescription = null
            )
        }
    }
}

@Composable
fun CounterTextField(
    modifier: Modifier = Modifier,
    currentString: String,
    onValueChange: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        BasicTextField(
            textStyle = themeTypography.body1.copy(color = themeColors.black),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
            singleLine = true,
            cursorBrush = SolidColor(themeColors.lime),
            value = currentString,
            onValueChange = onValueChange,
        )
    }
}

@Preview
@Composable
private fun IntCounterPreviewComposable() {
    TrainHardTheme {
        var value by remember { mutableIntStateOf(0) }
        Counter(
            initialValue = CounterValue.Int(value),
            increment = CounterValue.Float(1f)
        ) {
            value = it.value.toInt()
        }
    }
}

@Preview
@Composable
private fun FloatCounterPreviewComposable() {
    TrainHardTheme {
        var value by remember { mutableFloatStateOf(0f) }
        Counter(
            initialValue = CounterValue.Float(value),
            increment = CounterValue.Float(.25f)
        ) {
            value = it.value.toFloat()
        }
    }
}
