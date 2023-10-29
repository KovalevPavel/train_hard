package kovp.trainhard.core_domain

import java.text.SimpleDateFormat
import java.time.YearMonth
import java.time.ZoneId
import java.util.Date
import java.util.Locale

const val DATE_FORMAT_dd_newLine_MMMM = "dd\nMMMM"
const val DATE_FORMAT_LLLL_yyyy = "LLLL yyyy"

private val dateFormattersMap = mutableMapOf<String, SimpleDateFormat>()

fun Long.formatToDateString(dateFormat: String, locale: Locale = Locale.getDefault()): String =
    dateFormattersMap.getOrPut(dateFormat) {
        SimpleDateFormat(dateFormat, locale)
    }
        .format(this)

fun YearMonth.formatToDateString(
    dateFormat: String,
    locale: Locale = Locale.getDefault(),
): String = dateFormattersMap.getOrPut(dateFormat) {
    SimpleDateFormat(dateFormat, locale)
}
    .format(
        Date.from(this.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
    )
