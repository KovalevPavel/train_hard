package me.kovp.trainhard.components.counter

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType.Companion
import androidx.compose.ui.unit.dp
import me.kovp.components.R.drawable
import me.kovp.trainhard.components.counter.CounterValue.Float
import me.kovp.trainhard.core_domain.orZero
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography

@Composable
fun Counter(
    modifier: Modifier = Modifier,
    label: String = "",
    initialValue: CounterValue,
    increment: Float,
    onValueChanged: (CounterValue) -> Unit,
) {
    var currentValue by remember { mutableStateOf(initialValue) }
    var currentString by remember { mutableStateOf(initialValue.value.toString()) }

    val regex = when (initialValue::class) {
        CounterValue.Int::class -> "\\d{0,3}"
        Float::class -> "\\d{0,3}?\\.?\\d{0,2}"
        else -> ""
    }
        .toRegex()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = themeTypography.body1
        )
        Row {
            CounterButton(
                iconRes = drawable.ic_minus,
                isDecreaseButton = true,
            ) {
                val newValue = (currentValue - increment).value
                    .let {
                        when (initialValue) {
                            is CounterValue.Int -> it.toInt().coerceAtLeast(0)
                            else -> it.toFloat().coerceAtLeast(0f)
                        }
                    }
                    .toCounterValue()

                currentValue = newValue
                currentString = currentValue.value.toString()

                onValueChanged(currentValue)
            }
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .width(70.dp)
                    .background(color = themeColors.white)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                BasicTextField(
                    textStyle = themeTypography.body1.copy(color = themeColors.black),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = Companion.Number,
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(themeColors.lime),
                    value = currentString,
                    onValueChange = { rawString ->
                        val newString = rawString.takeIf { it.matches(regex) } ?: currentString
                        val newValue = when (initialValue) {
                            is CounterValue.Int -> {
                                newString.toIntOrNull()?.coerceAtLeast(0).orZero()
                            }

                            else -> {
                                newString.toFloatOrNull()?.coerceAtLeast(0f).orZero() as Number
                            }
                        }
                            .toCounterValue()

                        if (newString != currentString) {
                            currentValue = newValue
                            currentString = newString
                            onValueChanged(currentValue)
                        }
                    },
                )
            }
            CounterButton(
                iconRes = drawable.ic_plus,
                isDecreaseButton = false,
            ) {
                val newValue = (currentValue + increment).value
                    .let {
                        when (initialValue) {
                            is CounterValue.Int -> it.toInt()
                            else -> it.toFloat()
                        }
                    }
                    .toCounterValue()

                currentValue = newValue
                currentString = currentValue.value.toString()

                onValueChanged(currentValue)
            }
        }
    }
}

@Composable
private fun CounterButton(
    @DrawableRes iconRes: Int,
    isDecreaseButton: Boolean,
    onValueChange: () -> Unit
) {
    Surface(
        modifier = Modifier.size(40.dp),
        color = themeColors.lime,
        shape = RoundedCornerShape(
            topStart = if (isDecreaseButton) 20.dp else 0.dp,
            topEnd = if (isDecreaseButton) 0.dp else 20.dp,
            bottomEnd = if (isDecreaseButton) 0.dp else 20.dp,
            bottomStart = if (isDecreaseButton) 20.dp else 0.dp,
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
