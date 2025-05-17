package kovp.trainhard.home_presentation

import androidx.compose.runtime.Composable
import kovp.trainhard.home_presentation.components.CalendarCard
import kovp.trainhard.ui_theme.TrainHardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

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
