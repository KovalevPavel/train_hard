package me.kovp.trainhard.database.type_formatters

import androidx.room.TypeConverter

class CompletedExercisesListConverter {
    @TypeConverter
    fun convertToJson(data: List<Pair<Float, Int>>): String {
        return data.joinToString(SETS_DELIMITER) { (weight, reps) ->
            "$weight$WEIGHT_REPS_DELIMITER$reps"
        }
    }

    @TypeConverter
    fun convertFromJson(data: String): List<Pair<Float, Int>> {
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
