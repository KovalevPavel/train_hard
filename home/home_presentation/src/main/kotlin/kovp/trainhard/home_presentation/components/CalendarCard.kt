package kovp.trainhard.home_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import kotlin.math.roundToInt

private const val BOX_WIDTH_DP = 110

@Composable
fun CalendarCard(
    modifier: Modifier = Modifier,
    date: String?,
    onClick: () -> Unit,
) {
    date ?: return

    Surface(
        modifier = modifier.size(BOX_WIDTH_DP.dp),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = themeColors.lime,
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 6.dp,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val list = date.split("\n")

            val number = list.getOrElse(0) { return@Surface }
            val month = list.getOrElse(1) { return@Surface }

            Text(
                text = number,
                style = themeTypography.header1.copy(
                    color = themeColors.black,
                    fontSize = 36.sp,
                ),
                maxLines = 1,
            )

            MonthString(month)
        }
    }
}

@Composable
fun MonthString(month: String) {
    val measurer = rememberTextMeasurer()
    var textSizeSp = 20.sp
    val boxWidthPx = LocalDensity.current.run { (BOX_WIDTH_DP-16).dp.toPx().roundToInt() }

    while (
        measurer.measure(
            constraints = Constraints.fixedWidth(boxWidthPx),
            text = month,
            style = themeTypography.header1.copy(fontSize = textSizeSp),
            maxLines = 1,
        )
            .hasVisualOverflow
    ) {
        textSizeSp *= .95f
    }

    Text(
        text = month,
        style = themeTypography.header1.copy(
            color = themeColors.black,
            fontSize = textSizeSp,
        ),
        maxLines = 1,
        overflow = TextOverflow.Visible,
    )
}

@Preview
@Composable
private fun CalendarCardPreview(
    @PreviewParameter(DateStringProvider::class) date: String?,
) {
    TrainHardTheme {
        CalendarCard(date = date, onClick = {})
    }
}

private class DateStringProvider : PreviewParameterProvider<String?> {
    override val values: Sequence<String?>
        get() = sequenceOf(
            null,
            "",
            "03\nсентября",
            "13\nфевраля",
            "13\nфевраляяяяяя",
        )
}
