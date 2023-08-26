package me.kovp.trainhard.database.type_formatters

import androidx.room.TypeConverter

class IntListConverter {
    @TypeConverter
    fun toStringList(data: String): List<Int> = data.split(DATA_SEPARATOR).map(String::toInt)

    @TypeConverter
    fun toString(data: List<Int>) = data.joinToString(DATA_SEPARATOR)

    companion object {
        private const val DATA_SEPARATOR = ","
    }
}
