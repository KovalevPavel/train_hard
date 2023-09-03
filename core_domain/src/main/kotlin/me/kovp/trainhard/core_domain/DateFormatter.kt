package me.kovp.trainhard.core_domain

import java.text.SimpleDateFormat
import java.util.Locale

const val DATE_FORMAT_dd_newLine_MMMM = "dd\nMMMM"
const val DATE_FORMAT_dd_MM_yyyy = "dd.MM.yyyy"

private val dateFormattersMap = mutableMapOf<String, SimpleDateFormat>()

fun Long.formatToDateString(dateFormat: String, locale: Locale = Locale.getDefault()): String =
    dateFormattersMap.getOrPut (dateFormat){
    SimpleDateFormat(dateFormat, locale)
}
    .format(this)
