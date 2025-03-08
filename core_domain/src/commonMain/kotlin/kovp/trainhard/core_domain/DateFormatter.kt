@file:Suppress("TopLevelPropertyNaming")

package kovp.trainhard.core_domain

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

const val DATE_FORMAT_dd_MMMM = "dd\nMMMM"
const val DATE_FORMAT_LLLL_YYYY = "LLLL yyyy"

private val dateFormattersMap = mutableMapOf<String, DateTimeFormat<LocalDateTime>>()

@OptIn(FormatStringsInDatetimeFormats::class)
fun Instant.formatToDateString(dateFormat: String): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())

    // локали пока не поддерживаются в kotlinx-datetime
    return kotlin.runCatching {
        dateFormattersMap.getOrPut(dateFormat) {
            LocalDateTime.Format {
                byUnicodePattern(dateFormat)
            }
        }
    }
        .fold(
            onSuccess = { format -> format.format(localDateTime) },
            onFailure = { formatWithLocale(dateTime = localDateTime, format = dateFormat) }
        )


}

private fun formatWithLocale(dateTime: LocalDateTime, format: String): String {
    return when(format) {
        DATE_FORMAT_dd_MMMM -> {
            val month = when(dateTime.month) {
                Month.JANUARY -> "января"
                Month.FEBRUARY -> "февраля"
                Month.MARCH -> "марта"
                Month.APRIL -> "апреля"
                Month.MAY -> "мая"
                Month.JUNE -> "июня"
                Month.JULY -> "июля"
                Month.AUGUST -> "августа"
                Month.SEPTEMBER -> "сентября"
                Month.OCTOBER -> "октября"
                Month.NOVEMBER -> "ноября"
                Month.DECEMBER -> "декабря"
                else -> ""
            }

            val day = when(val d = dateTime.dayOfMonth) {
                in 1..9 -> "0$d"
                else -> d.toString()
            }

            return "$day\n$month"
        }

        DATE_FORMAT_LLLL_YYYY -> {
            val month = when(dateTime.month) {
                Month.JANUARY -> "январь"
                Month.FEBRUARY -> "февраль"
                Month.MARCH -> "март"
                Month.APRIL -> "апрель"
                Month.MAY -> "май"
                Month.JUNE -> "июнь"
                Month.JULY -> "июль"
                Month.AUGUST -> "август"
                Month.SEPTEMBER -> "сентябрь"
                Month.OCTOBER -> "октябрь"
                Month.NOVEMBER -> "ноябрь"
                Month.DECEMBER -> "декабрь"
                else -> ""
            }
            return "$month ${dateTime.year}"
        }

        else -> {
            dateTime.toString()
        }
    }
}
