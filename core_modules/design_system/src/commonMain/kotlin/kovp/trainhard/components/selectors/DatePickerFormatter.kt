package kovp.trainhard.components.selectors

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.datetime.Instant
import kovp.trainhard.core_domain.formatToDateString

@OptIn(ExperimentalMaterial3Api::class)
class DatePickerFormatter(
    private val noDateSelectedStub: String,
) : DatePickerFormatter {
    override fun formatDate(
        dateMillis: Long?,
        locale: CalendarLocale,
        forContentDescription: Boolean
    ): String {
        return dateMillis?.let(Instant::fromEpochMilliseconds)
            ?.formatToDateString(DATE_PICKER_FORMAT)
            ?: noDateSelectedStub
    }

    override fun formatMonthYear(monthMillis: Long?, locale: CalendarLocale): String? {
        return null
    }

    companion object {
        private const val DATE_PICKER_FORMAT = "dd.MM.uuuu"
    }
}
