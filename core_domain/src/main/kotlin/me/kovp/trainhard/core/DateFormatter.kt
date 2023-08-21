package me.kovp.trainhard.core

import java.text.SimpleDateFormat
import java.util.Locale

const val DATE_FORMAT_dd_newLine_MMMM = "dd\nMMMM"

private val dateFormattersMap = mutableMapOf<String, SimpleDateFormat>()

fun Long.formatToDateString(dateFormat: String, locale: Locale = Locale.ROOT): String =
    dateFormattersMap.getOrPut (dateFormat){
    SimpleDateFormat(dateFormat, locale)
}
    .format(this)
