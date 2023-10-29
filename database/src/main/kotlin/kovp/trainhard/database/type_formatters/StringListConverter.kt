package kovp.trainhard.database.type_formatters

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun toStringList(data: String): List<String> = data.split(DATA_SEPARATOR)

    @TypeConverter
    fun toString(data: List<String>) = data.joinToString(DATA_SEPARATOR)

    companion object {
        private const val DATA_SEPARATOR = ","
    }
}
