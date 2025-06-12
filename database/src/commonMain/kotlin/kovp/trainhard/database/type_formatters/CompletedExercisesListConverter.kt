package kovp.trainhard.database.type_formatters

import androidx.room.TypeConverter
import kovp.trainhard.database_api.models.Attempts

class CompletedExercisesListConverter {
    @TypeConverter
    fun convertToJson(data: Attempts): String {
        return data.joinToString(SETS_DELIMITER) { (weight, reps) ->
            "$weight$WEIGHT_REPS_DELIMITER$reps"
        }
    }

    @TypeConverter
    fun convertFromJson(data: String): Attempts {
        if (data.isEmpty()) return emptyList()
        return data.split(SETS_DELIMITER).mapNotNull {
            val (weightString, repsString) = it.split(WEIGHT_REPS_DELIMITER)
            val weight = weightString.toFloatOrNull() ?: return@mapNotNull null
            val reps = repsString.toIntOrNull() ?: return@mapNotNull null
            weight to reps
        }
    }

    companion object {
        private const val WEIGHT_REPS_DELIMITER = "_"
        private const val SETS_DELIMITER = ";"
    }
}
